package com.open.opgl

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix


/**
 * SurferView 设置
 */
fun initSurferView(surface:GLSurfaceView, renderer: GLSurfaceView.Renderer){
    surface.setEGLContextClientVersion(3)
    surface.setRenderer(renderer)
    surface.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
}


/**
 * 正交投影
 */
fun opa(height: Int, width: Int, uMatrixLocation: Int, mMatrix: FloatArray){
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
    GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)
}