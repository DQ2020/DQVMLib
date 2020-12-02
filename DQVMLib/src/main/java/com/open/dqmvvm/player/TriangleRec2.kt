package com.open.dqmvvm.player

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.dqmvvm.R
import com.open.dqmvvm.log.L
import kotlinx.android.synthetic.main.activity_g_l_sv.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

@Route(path = "/lib/rec2")
class TriangleRec2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_l_sv)

        glsv.setEGLContextClientVersion(2)
        glsv.setRenderer(MTR5())
//        glsv.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
}

class MTR5 : GLSurfaceView.Renderer {

    private val shapeT by lazy {
        ShapeT5()
    }

    override fun onDrawFrame(gl: GL10?) {
        L.d("onDrawFrame")
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BITS)
        gl?.glClearColor(1f, 1f, 1f, 1f)
        shapeT.rotate(0.000000001f * SystemClock.uptimeMillis())
        shapeT.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        L.d("onSurfaceChanged:::$width   $height")
        GLES20.glViewport(0, 0, width, height)

        val ratio = width * 1f / height
        L.d("onSurfaceChanged:::$ratio")
        shapeT.change(ratio)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        L.d("onSurfaceCreated")
        shapeT
    }
}

class ShapeT5 {
    var shapeData = floatArrayOf(
            0.5f,0.5f,0.5f,
            -0.5f,0.5f,0.5f,
            0.5f,0.5f,-0.5f,

            -0.5f,0.5f,0.5f,
            0.5f,0.5f,-0.5f,
            -0.5f,0.5f,-0.5f,

            0.5f,0.5f,-0.5f,
            -0.5f,0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,

            -0.5f,0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,

            0.5f,-0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f,

            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f,
            -0.5f,-0.5f,0.5f,

            0.5f,0.5f,0.5f,
            -0.5f,0.5f,0.5f,
            0.5f,-0.5f,0.5f,

            -0.5f,0.5f,0.5f,
            0.5f,-0.5f,0.5f,
            -0.5f,-0.5f,0.5f,

            0.5f,-0.5f,0.5f,
            0.5f,0.5f,0.5f,
            0.5f,-0.5f,-0.5f,

            0.5f,-0.5f,-0.5f,
            0.5f,0.5f,0.5f,
            0.5f,0.5f,-0.5f,

            -0.5f,0.5f,0.5f,
            -0.5f,-0.5f,0.5f,
            -0.5f,0.5f,-0.5f,

            -0.5f,0.5f,-0.5f,
            -0.5f,-0.5f,0.5f,
            -0.5f,-0.5f,-0.5f

    )
    var colorData = floatArrayOf(
            0f, 1f, 1f, 1f,
            0f, 1f, 1f, 1f,
            0f, 1f, 1f, 1f,
            0f, 1f, 1f, 1f,
            0f, 1f, 1f, 1f,
            0f, 1f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f)

    var shaderCode = "attribute vec4 vPosition;" +
            "uniform mat4 vMatrix;" +
            "varying  vec4 vColor;"+
            "attribute vec4 aColor;"+
            "void main() {" +
            "  gl_Position = vMatrix*vPosition;" +
            "  vColor = aColor;"+
            "}"
    private val framentShader = "precision mediump float;" +
            "varying vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"
    private var position: Int = -1
    private var matrix: Int = -1
    private var color: Int = -1
    private var program: Int = -1
    private val mViewMatrix = FloatArray(16)
    private val mProjectMatrix = FloatArray(16)
    private val mMVPMatrix = FloatArray(16)

    init {
        val glCreateShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(glCreateShader, shaderCode)
        GLES20.glCompileShader(glCreateShader)
        val glCreateShader2 = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(glCreateShader2, framentShader)
        GLES20.glCompileShader(glCreateShader2)
        program = GLES20.glCreateProgram()
        L.d("onSurfaceCreated-program:::$program")
        GLES20.glAttachShader(program, glCreateShader)
        GLES20.glAttachShader(program, glCreateShader2)
        GLES20.glLinkProgram(program)
        position = GLES20.glGetAttribLocation(program, "vPosition")
        L.d("onSurfaceCreated-v_Position:::$position")
        color = GLES20.glGetAttribLocation(program, "aColor")
        L.d("onSurfaceCreated-vColor:::$color")
        matrix = GLES20.glGetUniformLocation(program, "vMatrix")
    }

    fun draw() {
        GLES20.glUseProgram(program)
        GLES20.glUniformMatrix4fv(matrix, 1, false, mMVPMatrix, 0)
        GLES20.glEnableVertexAttribArray(position)
        val shapeBuf = ByteBuffer.allocateDirect(shapeData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(shapeData)
                .apply {
                    position(0)
                }

        val colorBuf = ByteBuffer.allocateDirect(colorData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(colorData)
                .apply {
                    position(0)
                }
        GLES20.glVertexAttribPointer(matrix, 3, GLES20.GL_FLOAT, false, 12, shapeBuf)
        GLES20.glUniform4fv(color, 1, colorBuf)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, shapeData.size / 3)
        GLES20.glEnableVertexAttribArray(color)
        GLES20.glVertexAttribPointer(color,4,GLES20.GL_FLOAT,false,0,colorBuf)
        GLES20.glDisableVertexAttribArray(position)
        GLES20.glDisable(position)
        GLES20.glDisable(color)
    }

    fun change(ratio: Float) {
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 20f)
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, 5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0)
    }

    fun rotate(radio: Float) {
        Matrix.rotateM(mMVPMatrix, 0, radio, 1f, 1f, 1f)
    }

}
