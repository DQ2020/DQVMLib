package com.open.dqmvvm.player

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.Paint
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.SurfaceHolder
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.dqmvvm.R
import com.open.dqmvvm.log.L
import kotlinx.android.synthetic.main.activity_camera.*


@Route(path = "/lib/camera")
@SuppressLint("MissingPermission")
class CameraPlayer : Activity(),SurfaceHolder.Callback {


    private val mHandler: Handler? = null
    private val mSessionCaptureCallback: CameraCaptureSession.CaptureCallback? = null
    private var mPreviewBuilder: CaptureRequest.Builder? = null
    private var mSession: CameraCaptureSession? = null
    private var mState: Int = 0
    private var mImageReader: ImageReader? = null
    private var mCamera: CameraDevice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),2020)

        view.holder.addCallback(this)
        start.setOnClickListener {
            try {
                L.d("take picture")
                mState = 0
                mSession?.setRepeatingRequest(mPreviewBuilder!!.build(), mSessionCaptureCallback, mHandler)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        L.d("surfaceCreated")
        val thread = HandlerThread("c2")
        thread.start()
//        initIcon(holder)
        Handler(thread.looper)
                .also {
                    mImageReader = ImageReader.newInstance(view.width,view.height,ImageFormat.JPEG,7).apply {
                        setOnImageAvailableListener({
                            L.d("onImageAvailable")
                        }, Handler(Looper.getMainLooper()))
                    }
                    (this.getSystemService(Context.CAMERA_SERVICE) as CameraManager).openCamera(CameraCharacteristics.LENS_FACING_BACK.toString(), object : CameraDevice.StateCallback(){
                        override fun onOpened(camera: CameraDevice) {
                            L.d("onOpened")
                            mCamera = camera
                            camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
                                mPreviewBuilder = this
                            }.apply {
                                addTarget(view.holder.surface)
                            }
                            camera.createCaptureSession(arrayListOf(view.holder.surface,mImageReader!!.surface),object :CameraCaptureSession.StateCallback(){
                                override fun onConfigureFailed(session: CameraCaptureSession) {
                                }

                                override fun onConfigured(session: CameraCaptureSession) {
                                    mSession = session
                                    mPreviewBuilder?.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                                    mSession?.setRepeatingRequest(mPreviewBuilder!!.build(), mSessionCaptureCallback, mHandler)
                                }
                            },it)
                        }

                        override fun onDisconnected(camera: CameraDevice) {
                        }

                        override fun onError(camera: CameraDevice, error: Int) {
                        }
                    },it)
                }
    }

    private fun initIcon(holder: SurfaceHolder?) {
        val canvas = holder?.lockCanvas()
        val paint = Paint()
        paint.color = Color.BLUE
        canvas?.drawLine(10f,50f,300f,70f,paint)
        holder?.unlockCanvasAndPost(canvas)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCamera?.close()
        mCamera = null
    }
}