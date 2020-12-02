package com.open.dqcp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.io.File

const val PERMISSION_REQ = 1000
const val CAMERA_REQ = 1001
const val PHOTO_REQ = 1001

@RequiresApi(Build.VERSION_CODES.N)
fun show(context:Context){
    if (permission(context)){
        return
    }
    AlertDialog.Builder(context)
            .setTitle("图库")
            .setItems(arrayOf("相机","图片")){dialog, which ->
                when(which){
                    0-> {
                        (context as Activity).startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_REQ)
                    }
                    else-> {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "image/*"
                        (context as Activity).startActivityForResult(intent,PHOTO_REQ)
                    }
                }
                dialog.dismiss()
            }
            .create()
            .show()
}

fun permission(context:Context):Boolean{
    val permission = mutableListOf<String>()
    if (ActivityCompat.checkSelfPermission(context,Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
        permission.add(Manifest.permission.CAMERA)
    }
    if (ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
        permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    if (permission.size > 0){
        ActivityCompat.requestPermissions(context as Activity,permission.toTypedArray(), PERMISSION_REQ)
        return true
    }
    return false
}

fun getBitMap(requestCode: Int, resultCode: Int, data: Intent?):Bitmap? {
    if (resultCode != Activity.RESULT_OK){
        return null
    }
    if (requestCode == CAMERA_REQ){
        return data?.extras?.get("data") as Bitmap
    }
    return null
}

fun getUri(requestCode: Int, resultCode: Int, data: Intent?): Uri? {
    if (resultCode != Activity.RESULT_OK){
        return null
    }
    if (requestCode == CAMERA_REQ){
        Log.d("DQ2020",data?.data?.toString()!!)
        return data?.data
    }
    if (requestCode == PHOTO_REQ){
        Log.d("DQ2020",data?.data?.toString()!!)
        return data?.data
    }
    return null
}