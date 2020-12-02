package com.open.dqmvvm.main

import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.open.dqmvvm.BR
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.base.BaseHolder
import com.open.dqmvvm.base.BaseRvAdapter
import com.open.dqmvvm.databinding.ActivityMainBinding
import com.open.dqmvvm.login.LoginActivity
import com.open.dqmvvm.share_element_anim.ShareElementAnimActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.hypot

class MainActivity : BaseActivity<ActivityMainBinding,MainVM>() {

    override fun getView(): Int {
        return R.layout.activity_main
    }

    override fun getVmId(): Int {
        return BR.main
    }

    override fun getViewModel(): MainVM {
        return ViewModelProvider(this)[MainVM::class.java]
                .apply {
                    title.value = "DQVM(DQ's MVVM Project)"
                }
    }

    override fun init() {
        initList()
//        show.apply {
//            alpha = 0f
//            visibility = View.GONE
//            animate().apply {
//                alpha(1f)
//                duration = 2000
//                start()
//            }
//        }
//       show.post {
//           showView()
//       }
    }

    private fun initList() {
        list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false)
            adapter = object : BaseRvAdapter<VMItem>(this@MainActivity){
                override fun onBindViewHolder(holder: BaseHolder, position: Int) {
                    holder.bind(items[position])
                }

                override fun initLayout(type: Int): Int {
                    return R.layout.item_vm
                }

            }.apply {
                addItems(mutableListOf(
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素1", ShareElementAnimActivity::class.java),
                        VMItem("共享元素3", ShareElementAnimActivity::class.java),
                        VMItem("共享元素4", ShareElementAnimActivity::class.java),
                        VMItem("共享元素5", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java),
                        VMItem("共享元素", ShareElementAnimActivity::class.java)

                ))
            }
        }
        GlobalScope.launch {
        }
    }

    fun showView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = show.width / 2
            val cy = show.height / 2

            val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val anim = ViewAnimationUtils.createCircularReveal(show, cx, cy, 0f, finalRadius)
            show.visibility = View.VISIBLE
            anim.duration = 2000
            anim.start()
        } else {
            show.visibility = View.VISIBLE
        }
    }

    fun startActivity(v:View){
        ActivityCompat.startActivity(this,
                Intent(this,LoginActivity::class.java),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,image,resources.getString(R.string.app_name)).toBundle())
    }
}