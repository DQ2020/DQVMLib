package com.open.dqmvvm.bind

import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.open.dqmvvm.v.Labels

object BindEvent {

    @JvmStatic
    @BindingAdapter(value = ["isLastSelection"])
    fun setLastSelection(v: EditText, isLastSelection: Boolean) {
        Log.d("2020", "setLastSelection")
//        if (isLastSelection)
//            with(v) {
//                Log.d("2020", "text size: ".plus(length()))
//                setSelection(length())
//            }
    }

    @JvmStatic
    @BindingAdapter("app:labelText")
    fun labelText(label:Labels,text:String){
        label.initLabels(listOf(text))
    }
}