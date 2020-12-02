package com.open.dqmvvm.picture

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.open.dqmvvm.web.BaseWebView
import java.io.File

object PicturePicker {

    const val CAMERA = 0x2020
    const val ALBUM = 0x2021
    const val ALBUMS = 0x2022

    fun show(context: Context, camera: Int, album: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("选取方式：")
        builder.setNegativeButton("取消", null)
        builder.setItems(arrayOf<String>().apply {
            var i = 0
            if (camera > 0) this[i++] = "拍照"
            if (album > 0) this[i] = "相册"
        }) { _, i ->
            if (camera > 0 && i == 0) camera(context)
            else if (album > 0) album(context, album)
        }
        builder.create().show()
    }


    fun camera(context: Context) {
        Log.d("DQ2020", "camera")
        val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path!!)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "/temp.jpg")
        Log.d("DQ2020", file.absolutePath)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, "com.open.dqmvvm.fileprovider", file))
        (context as Activity).startActivityForResult(intent, CAMERA)
    }

    /**
     * 系统原生相册
     */
    @Deprecated("系统原生相册")
    private fun album(activity: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        activity.startActivityForResult(intent, ALBUM)
    }

    private fun album(context: Context, num: Int) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2020)
            return
        }
        val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Media.MIME_TYPE.plus("=?"),
                arrayOf("image/png"), null)
        while (cursor?.moveToNext()!!) {
            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            Log.d("DQ2020", path)
        }
    }

    private fun handleImageOnKitKat(context: Context, data: Intent?): String {
        var imagePath = ""
        val uri = data?.data
        if (DocumentsContract.isDocumentUri(context, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri?.authority) {
                val id = docId.split(":")[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath =
                        getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri?.authority) {
                val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        docId.toLong()
                )
                imagePath = getImagePath(context, contentUri, null)
            }
        } else if ("content" == uri?.scheme) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(context, uri, null);
        } else if ("file" == uri?.scheme) {
            imagePath = uri.path!!
        }
        return imagePath
    }

    private fun getImagePath(context: Context, uri: Uri, selection: String?): String {
        var path = ""
        val cursor = context.contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close()
        }
        return path
    }

    fun onActivityResult(context: Context, web: BaseWebView, requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == ALBUM && resultCode == Activity.RESULT_OK) {
            val uri = intent?.data
            Log.d("DQ2020", "uri:::::: ${uri.toString()}")
            val path = uri?.path
            Log.d("DQ2020", "path:::::: $path")
            val real = handleImageOnKitKat(context, intent)
            Log.d("DQ2020", "real:::::: $real")
//            Util.compressAndUpload(context, web, File(real))
        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
            //原获取缩微图 改为 高清图
            //val path = intent?.extras?.get("data") as Bitmap
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path?.plus("/temp.jpg")!!)
            Log.d("DQ2020", file.absolutePath)
//            Util.compressAndUpload(context, web, file)
        }
    }
}