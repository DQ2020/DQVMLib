package com.open.dqmvvm.picture

import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseHolder
import com.open.dqmvvm.base.BaseRvAdapter
import kotlinx.android.synthetic.main.activity_picture_list.*

@Route(path = "/lib/pictures")
class PictureListActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_list)

        initUI()
    }

    private fun initUI() {
        val pics = mutableListOf<PictureM>()
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Media.MIME_TYPE.plus("=?").plus(" or ").plus(MediaStore.Images.Media.MIME_TYPE).plus("=?"),
                arrayOf("image/jpeg","image/png"), MediaStore.Images.Media.DATE_MODIFIED.plus(" desc"))
        while (cursor?.moveToNext()!!){
            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            Log.d("DQ2020",path)
            pics.add(PictureM(path,false))
        }
        pictures.adapter = object : BaseRvAdapter<PictureM>(this) {
            override fun onBindViewHolder(holder: BaseHolder, position: Int) {
                val item = items[position]
                val view = holder.getView<ImageView>(R.id.pic)
                val flag = holder.getView<ImageView>(R.id.flag)
                Glide.with(context)
                        .load(item.url)
                        .into(view)
                flag.isSelected = item.flag
                flag.setOnClickListener {
                    item.flag = !item.flag
                    flag.isSelected = item.flag
                }
            }

            override fun initLayout(type: Int): Int {
                return R.layout.item_picture
            }
        }.apply {
            initItems(pics)
        }
        pictures.layoutManager = GridLayoutManager(this,4)
    }
}

data class PictureM(val url:String,var flag:Boolean)