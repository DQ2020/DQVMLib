package com.open.opgl

import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.opengl.Matrix
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.open.dqmvvm.R
import kotlinx.android.synthetic.main.activity_triangle.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TriangleMatrixTexture : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangle)
        surface.setEGLContextClientVersion(3)
        surface.setRenderer(TriangleRenderer())
        surface.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    inner class TriangleRenderer : GLSurfaceView.Renderer {

        var shape: Shape? = null
        override fun onDrawFrame(gl: GL10?) {
            //清除surface背景色
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
            //填充surface背景色
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1f)
            shape?.beforeDraw()
//            shape?.drawPoint()
//            shape?.drawLine()
            shape?.drawTriangle()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0, 0, width, height)
            shape?.changed(height, width)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            shape = Shape()
        }
    }

    inner class Shape {
        val vertex_data = "#version 300 es\n" +
                "layout (location = 0) in vec4 vPosition;\n" +
                "layout (location = 1) in vec2 aTextureCoord;\n" +
                "out vec2 vTexCoord;\n" +
                "uniform mat4 u_Matrix;\n" +
                "void main() { \n" +
                "     gl_Position  = u_Matrix * vPosition;\n" +
                "     gl_PointSize = 10.0;\n" +
                "     vTexCoord = aTextureCoord;\n" +
                "}"

        val fragment_data = "#version 300 es\n" +
                "precision mediump float;\n" +
                "uniform sampler2D uTextureUnit;\n" +
                "in vec2 vTexCoord;\n" +
                "out vec4 vFragColor;\n" +
                "void main() {\n" +
                "     vFragColor = texture(uTextureUnit,vTexCoord);\n" +
                "}"

        val triangleVertexPointArr = floatArrayOf(
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        )

        var triangleTextureData = floatArrayOf(
                0f, 0f,
                0f, 1f,
                1f, 1f,
                1f, 0f
        )

        val vertexBuffer = ByteBuffer.allocateDirect(triangleVertexPointArr.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(triangleVertexPointArr)
                .position(0)

        val textureBuffer = ByteBuffer.allocateDirect(triangleTextureData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(triangleTextureData)
                .position(0)

        var textureIds = IntArray(1)

        val mMatrix = FloatArray(16)
        var uMatrixLocation: Int? = null
        var aPositionLocation: Int? = null
        var aColorLocation: Int? = null

        init {
            //创建程序
                        GLES30.glCreateProgram()
                                .also { programId ->
                        //创建一个顶点着色器
                        GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER)
                                .also {
                                    //设置数据源
                                    GLES30.glShaderSource(it, vertex_data)
                                    //编译
                                    GLES30.glCompileShader(it)
                                    val status = intArrayOf(1)
                                    GLES30.glGetShaderiv(it, GLES30.GL_COMPILE_STATUS, status, 0);
                                    if (status[0] == 0) {
                                        GLES30.glGetShaderInfoLog(it)
                                                .also {
                                                    Log.d("DQ2020", " create VERTEX_SHADER errorInfo:$it")
                                                }
                                        GLES30.glDeleteShader(it);
                                    }
                                    //关联到程序
                                    GLES30.glAttachShader(programId, it)
                                    Log.d("DQ2020", "vertexId$it")
                                }
                        //创建一个片段着色器
                        GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER)
                                .also {
                                    //设置数据源
                                    GLES30.glShaderSource(it, fragment_data)
                                    //编译
                                    GLES30.glCompileShader(it)
                                    val status = intArrayOf(1)
                                    GLES30.glGetShaderiv(it, GLES30.GL_COMPILE_STATUS, status, 0);
                                    if (status[0] == 0) {
                                        GLES30.glGetShaderInfoLog(it)
                                                .also {
                                                    Log.d("DQ2020", " create FRAGMENT_SHADER errorInfo:$it")
                                                }
                                        GLES30.glDeleteShader(it);
                                    }
                                    //关联到程序
                                    GLES30.glAttachShader(programId, it)
                                    Log.d("DQ2020", "fragmentId$it")
                                }
                        //创建纹理
                        GLES30.glGenTextures(1, textureIds, 0)
                        if (textureIds[0] == 0) {
                            Log.d("DQ2020", "create texture fail")
                        } else {
                            Log.d("DQ2020", "create texture success")
                            BitmapFactory.decodeResource(application.resources, R.drawable.ic_logo, BitmapFactory.Options())
                                    .also {
                                        if (null == it) {
                                            Log.d("DQ2020", "decodeResource texture fail")
                                            GLES30.glDeleteTextures(1, textureIds, 0)
                                        }
                                        // 绑定纹理到OpenGL
                                        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0])
                                        //设置默认的纹理过滤参数
                                        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR)
                                        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR)
                                        // 加载bitmap到纹理中
                                        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, it, 0);
                                        // 生成MIP贴图
                                        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D)
                                        // 取消绑定纹理
                                        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0)
                                        // 数据如果已经被加载进OpenGL,则可以回收该bitmap
                                        it.recycle()
                                    }
                        }
                        GLES30.glLinkProgram(programId)
                        val intArrayOf = intArrayOf(1)
                        GLES30.glGetProgramiv(programId, GLES30.GL_LINK_STATUS, intArrayOf, 0)
                        if (intArrayOf[0] == 0) {
                            GLES30.glGetProgramInfoLog(programId)
                                    .also {
                                        Log.d("DQ2020", "errorInfo:$it")
                                    }
                            GLES30.glDeleteProgram(programId)
                        }
                        GLES30.glUseProgram(programId)
                        uMatrixLocation = GLES30.glGetUniformLocation(programId, "u_Matrix")
                        aPositionLocation = GLES30.glGetAttribLocation(programId, "vPosition");
                        aColorLocation = GLES30.glGetAttribLocation(programId, "aColor")
                        Log.d("DQ2020", "programId$programId")
                        Log.d("DQ2020", when (intArrayOf[0]) {
                            0 -> "program创建失败";else -> "program创建成功"
                        })
                    }
        }

        fun beforeDraw() {
            GLES30.glUniformMatrix4fv(uMatrixLocation!!, 1, false, mMatrix, 0)
        }

        fun drawTriangle() {
            Log.d("DQ2020", "drawTriangle")
            GLES30.glEnableVertexAttribArray(0)
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer)
            GLES30.glEnableVertexAttribArray(1)
            GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, textureBuffer)
            //激活纹理
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
            //绑定纹理
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0])
            GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4)
            GLES30.glDisableVertexAttribArray(0)
        }

        fun changed(height: Int, width: Int) {
            when {
                height > width -> {
                    val radio = height.toFloat() / width
                    Matrix.orthoM(mMatrix, 0, -1f, 1f, -radio, radio, -1f, 1f)
                }
                else -> {
                    val radio = width.toFloat() / height
                    Matrix.orthoM(mMatrix, 0, -radio, radio, -1f, 1f, -1f, 1f)
                }
            }
        }
    }
}