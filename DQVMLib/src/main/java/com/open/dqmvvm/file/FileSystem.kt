package com.open.dqmvvm.file

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.dqmvvm.log.L

@Route(path = "/lib/file")
class FileSystem : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(TextView(this))
        L.d("getDataDirectory${Environment.getDataDirectory().absolutePath}")
        L.d("getDownloadCacheDirectory${Environment.getDownloadCacheDirectory().absolutePath}")
        L.d("getExternalStorageDirectory${Environment.getExternalStorageDirectory().absolutePath}")
        L.d("getExternalStoragePublicDirectory${Environment.getExternalStoragePublicDirectory("2").absolutePath}")
        L.d("getExternalFilesDir${getExternalFilesDir("1")}")
        L.d("getDataDir${dataDir.absolutePath}")
        L.d("getFilesDir${filesDir.absolutePath}")
        L.d("getCacheDir${cacheDir.absolutePath}")
        L.d("getFileStreamPath${getFileStreamPath("1").absolutePath}")
    }
}


//D/DQ2020: E-getExternalStorageDirectory                /storage/emulated/0
//D/DQ2020: E-getExternalStoragePublicDirectory          /storage/emulated/0/2
//D/DQ2020: Environment-getDataDirectory                 /data
//D/DQ2020: Environment-getDownloadCacheDirectory        /data/cache

//D/DQ2020: getExternalFilesDir                          /storage/emulated/0/Android/data/com.movit.dqtest/files/1
//D/DQ2020: getDataDir                                   /data/user/0/com.movit.dqtest
//D/DQ2020: getFilesDir                                  /data/user/0/com.movit.dqtest/files
//D/DQ2020: getCacheDir                                  /data/user/0/com.movit.dqtest/cache
//D/DQ2020: getFileStreamPath                            /data/user/0/com.movit.dqtest/files/1