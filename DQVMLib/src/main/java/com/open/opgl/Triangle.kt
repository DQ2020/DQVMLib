package com.open.opgl

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.open.dqmvvm.R
import kotlinx.android.synthetic.main.activity_triangle.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class Triangle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangle)
        surface.setEGLContextClientVersion(3)
        surface.setRenderer(TriangleRenderer())
        surface.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    class TriangleRenderer : GLSurfaceView.Renderer {

        var shape:Shape? = null

        override fun onDrawFrame(gl: GL10?) {
            //清除surface背景色
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
            //填充surface背景色
            GLES30.glClearColor(0.5f,0.5f,0.5f,1f)
//            shape?.drawPoint()
//            shape?.drawLine()
            shape?.drawTriangle()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0,0,width,height)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            shape = Shape()
        }
    }

    class Shape {
        val vertex_data = "#version 300 es\n" +
                "layout (location = 0) in vec4 vPosition;\n" +
                "void main() {\n" +
                "     gl_Position  = vPosition;\n" +
                "     gl_PointSize = 10.0;\n" +
                "}"

        val fragment_data = "#version 300 es\n" +
                "precision mediump float;\n" +
                "out vec4 fragColor;\n" +
                "void main() {\n" +
                "     fragColor = vec4(1.0,1.0,1.0,1.0);\n" +
                "}"

        val triangleVertexPointArr = floatArrayOf(
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f)

        var triangleVertexColorData = floatArrayOf(
                1f,1f,1f,1f
        )

        val vertexBuffer = ByteBuffer.allocateDirect(triangleVertexPointArr.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(triangleVertexPointArr)
                .position(0)

        val colorBuffer = ByteBuffer.allocateDirect(triangleVertexColorData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(triangleVertexColorData)
                .position(0)

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
                                    if (status[0] == 0){
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
                                    if (status[0] == 0){
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
                        GLES30.glLinkProgram(programId)
                        val intArrayOf = intArrayOf(1)
                        GLES30.glGetProgramiv(programId,GLES30.GL_LINK_STATUS, intArrayOf,0)
                        if (intArrayOf[0] == 0){
                            GLES30.glGetProgramInfoLog(programId)
                                    .also {
                                        Log.d("DQ2020", "errorInfo:$it")
                                    }
                            GLES30.glDeleteProgram(programId)
                        }
                        GLES30.glUseProgram(programId)
                        Log.d("DQ2020", "programId$programId")
                        Log.d("DQ2020", when(intArrayOf[0]){0->"program创建失败";else->"program创建成功"})
                    }
        }

        fun drawPoint(){
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer)
            GLES30.glEnableVertexAttribArray(0)
            GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 3)
            GLES30.glDisableVertexAttribArray(0)
        }

        fun drawLine(){
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer)
            GLES30.glEnableVertexAttribArray(0)
            GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, 3)
            GLES30.glLineWidth(1f)
        }

        fun drawTriangle(){
            GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,0,vertexBuffer)
            GLES30.glEnableVertexAttribArray(0)
            GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, colorBuffer);
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
            GLES30.glDisableVertexAttribArray(0)
        }
    }
}