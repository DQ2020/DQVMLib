package com.open.dqmvvm.v.pickers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.open.dqmvvm.log.L

class Pickers(context: Context) : ViewGroup(context) {

    private val maxHeight = 800

    fun show() {
        addView(initTitle(), 0)
        ((context as Activity).window.decorView as ViewGroup).addView(this, decorLayoutParams())
    }

    private fun dismiss() {
        ((context as Activity).window.decorView as ViewGroup).removeView(this)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        L.d("onLayout")
        getChildAt(0).apply {
            layout(0, 0, measuredWidth, measuredHeight)
        }
        getChildAt(1).apply {
            layout(0, getChildAt(0).measuredHeight, measuredWidth, getChildAt(0).measuredHeight + measuredHeight)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        L.d("onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        L.d("pickers parent height: $parentHeight")
        val selfHeight = if (parentHeight > maxHeight) {
            maxHeight
        } else {
            parentHeight
        }
        measureChild(getChildAt(0), widthMeasureSpec, MeasureSpec.makeMeasureSpec(selfHeight, MeasureSpec.AT_MOST))
        L.d("onMeasure title width:${getChildAt(0).measuredWidth}")
        L.d("onMeasure title height:${getChildAt(0).measuredHeight}")
        measureChild(getChildAt(1), widthMeasureSpec, MeasureSpec.makeMeasureSpec(selfHeight - getChildAt(0).measuredHeight, MeasureSpec.AT_MOST))
        L.d("onMeasure sheet width:${getChildAt(1).measuredWidth}")
        L.d("onMeasure sheet height:${getChildAt(1).measuredHeight}")
        setMeasuredDimension(widthMeasureSpec, selfHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        L.d("onDraw")
    }

    @SuppressLint("ResourceType")
    private fun initTitle() = RelativeLayout(context).apply {
        setBackgroundColor(Color.WHITE)
        layoutParams = LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
        }
        addView(TextView(context).apply {
            text = "取消"
            setPadding(50, 20, 50, 20)
            setTextColor(Color.BLUE)
            setOnClickListener { dismiss() }
        }, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_START)
        })
        addView(TextView(context).apply {
            id = 0x2020000
            text = "确定"
            setPadding(50, 20, 50, 20)
            setTextColor(Color.BLUE)
            setOnClickListener {
                getWheel(1).out()
                dismiss()
            }
        }, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_PARENT_END)
        })
        addView(View(context).apply {
            setBackgroundColor(Color.LTGRAY)
        }, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1).apply {
            addRule(RelativeLayout.BELOW, 0x2020000)
        })
    }

    private fun decorLayoutParams() = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
        gravity = Gravity.BOTTOM
        setMargins(0, 0, 0, 100)
    }


    private fun getWheel(index: Int): PickerWheel<*> {
        return getChildAt(index) as PickerWheel<*>
    }

    fun addYearWheel(lis: (Int) -> Unit): Pickers {
        addView(PickerWheel<Int>(context).init(mutableListOf<Int>().apply {
            for (i in 1990..2020) {
                add(i)
            }
        }, lis))
        return this
    }

    inline fun <reified T> addWheel(data: List<T>, noinline lis: (T) -> Unit): Pickers {
        addView(PickerWheel<T>(context).init(data, lis))
        return this
    }
}