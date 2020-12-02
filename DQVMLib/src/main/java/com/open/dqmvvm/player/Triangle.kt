package com.open.dqmvvm.player

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.dqmvvm.R
import com.open.dqmvvm.log.L
import kotlinx.android.synthetic.main.activity_g_l_sv.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

@Route(path = "/lib/triangle")
class Triangle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_l_sv)

        glsv.setEGLContextClientVersion(2)
        glsv.setRenderer(MTR())
        glsv.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
}

class MTR : GLSurfaceView.Renderer{

    private val shapeT by lazy {
        ShapeT()
    }

    private val shapeR by lazy {
        ShapeR()
    }

    override fun onDrawFrame(gl: GL10?) {
        L.d("onDrawFrame")
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BITS)
        gl?.glClearColor(1f,1f,1f,1f)
        shapeT.draw()
        shapeR.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        L.d("onSurfaceChanged")
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        L.d("onSurfaceCreated")
        shapeT
        shapeR
    }
}

class ShapeT {
    var shapeData = floatArrayOf(
            0f,0.5f,
            -0.5f,0f,
            0.5f,0f)

    var colorData = floatArrayOf(
            1f,0f,0f,1f
    )

    var shaderCode = "attribute vec4 vPosition; void main(){ gl_Position = vPosition;}"
    private val framentShader = "precision mediump float; uniform vec4 vColor; void main(){gl_FragColor= vColor;}"
    private var v_Position:Int = -1
    private var c_Position:Int = -1
    private var program:Int = -1

    init {
        val glCreateShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(glCreateShader,shaderCode)
        GLES20.glCompileShader(glCreateShader)
        val glCreateShader2 = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(glCreateShader2,framentShader)
        GLES20.glCompileShader(glCreateShader2)
        program = GLES20.glCreateProgram()
        L.d("onSurfaceCreated-program:::$program")
        GLES20.glAttachShader(program,glCreateShader)
        GLES20.glAttachShader(program,glCreateShader2)
        GLES20.glLinkProgram(program)
        v_Position = GLES20.glGetAttribLocation(program,"vPosition")
        L.d("onSurfaceCreated-v_Position:::$v_Position")
        c_Position = GLES20.glGetUniformLocation(program,"vColor")
        L.d("onSurfaceCreated-vColor:::$c_Position")
    }

    fun draw(){
        GLES20.glUseProgram(program)
        GLES20.glEnableVertexAttribArray(v_Position)
        GLES20.glEnable(c_Position)
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
        GLES20.glVertexAttribPointer(v_Position,2,GLES20.GL_FLOAT,false,8,shapeBuf)
        GLES20.glUniform4fv(c_Position,1,colorBuf)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,3)
        GLES20.glDisable(v_Position)
        GLES20.glDisable(c_Position)
    }
}

class ShapeR {
    var shapeData = floatArrayOf(
            -0.5f,0f,
            -0.5f,-0.5f,
            0.5f,0.0f,
            0.5f,-0.5f)

    var colorData = floatArrayOf(
            1f,1f,1f,1f
    )

    var shaderCode = "attribute vec4 vPosition; void main(){ gl_Position = vPosition;}"
    private val framentShader = "precision mediump float; uniform vec4 vColor; void main(){gl_FragColor= vColor;}"
    private var v_Position:Int = -1
    private var c_Position:Int = -1
    private var program:Int = -1

    init {
        val glCreateShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(glCreateShader,shaderCode)
        GLES20.glCompileShader(glCreateShader)
        val glCreateShader2 = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(glCreateShader2,framentShader)
        GLES20.glCompileShader(glCreateShader2)
        program = GLES20.glCreateProgram()
        L.d("onSurfaceCreated-program:::$program")
        GLES20.glAttachShader(program,glCreateShader)
        GLES20.glAttachShader(program,glCreateShader2)
        GLES20.glLinkProgram(program)
        v_Position = GLES20.glGetAttribLocation(program,"vPosition")
        L.d("onSurfaceCreated-v_Position:::$v_Position")
        c_Position = GLES20.glGetUniformLocation(program,"vColor")
        L.d("onSurfaceCreated-vColor:::$c_Position")
    }

    fun draw(){
        GLES20.glUseProgram(program)
        GLES20.glEnableVertexAttribArray(v_Position)
        GLES20.glEnable(c_Position)
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
        GLES20.glVertexAttribPointer(v_Position,2,GLES20.GL_FLOAT,false,8,shapeBuf)
        GLES20.glUniform4fv(c_Position,1,colorBuf)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4)
        GLES20.glDisable(v_Position)
        GLES20.glDisable(c_Position)
    }
}

