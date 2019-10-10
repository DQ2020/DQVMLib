package com.open.dqmvvm.util

import android.app.AlertDialog
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.open.dqmvvm.R

class Loading(context: Context) : AlertDialog(context, R.style.tranAlertDialog), DefaultLifecycleObserver {

    override fun show() {
        if (!isShowing) {
            super.show()
            val progressBar = ProgressBar(context)
            setContentView(progressBar)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        L.d("ui over so loading over")
        if (isShowing) {
            dismiss()
        }
    }
}