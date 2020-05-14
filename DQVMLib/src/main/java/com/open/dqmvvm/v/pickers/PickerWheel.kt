package com.open.dqmvvm.v.pickers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.OverScroller
import androidx.core.view.ViewCompat
import com.open.dqmvvm.log.L
import kotlin.math.abs

class PickerWheel<T>(context: Context?) : View(context) {

    private val lineHeight = 100
    private val paint = Paint()
    private var dy = 0f
    private lateinit var data: List<T>
    private lateinit var lis: (T) -> Unit
    private val obtain = VelocityTracker.obtain()
    private var scroller = OverScroller(context) { v ->
        return@OverScroller v
    }

    init {
        paint.color = Color.BLACK
        paint.textSize = 45f
        paint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var selfHeight = 0
        for ((index, _) in data.withIndex()) {
            selfHeight = 100 + 100 * index
        }
        L.d("wheel real height:$selfHeight")
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        L.d("wheel parent height:$parentHeight")
        if (selfHeight > parentHeight) {
            selfHeight = parentHeight
        }
        L.d("wheel final height:$parentHeight")
        setMeasuredDimension(measuredWidth, selfHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.WHITE)
        paint.color = Color.LTGRAY
        val lineTop = (measuredHeight - lineHeight) / 2f + 1 + scrollY
        canvas?.drawLine(0f, (measuredHeight - lineHeight) / 2f + scrollY, measuredWidth.toFloat(), lineTop, paint)
        val lineBottom = (measuredHeight + lineHeight) / 2f + 1 + scrollY
        canvas?.drawLine(0f, (measuredHeight + lineHeight) / 2f + scrollY, measuredWidth.toFloat(), lineBottom, paint)
        for ((index, item) in data.withIndex()) {
            val txtY = (measuredHeight + wordHeight() - 12f) / 2f + lineHeight * index
            if (txtY > lineTop && txtY < lineBottom) {
                paint.color = Color.BLACK
            }
            canvas?.drawText(item.toString(), (measuredWidth - wordLength(item.toString())) / 2, txtY, paint)
            paint.color = Color.LTGRAY
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        obtain.addMovement(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                dy = event.y
                return true
            }

            MotionEvent.ACTION_UP -> {
                scrollBy(0, if (scrollY % lineHeight > lineHeight % 2) -scrollY % lineHeight else scrollY % lineHeight)
                obtain.computeCurrentVelocity(1000, 6000f)
                val yVelocity = obtain.getYVelocity(event.getPointerId(0))
                L.d("yVelocity$yVelocity")
                keepScroll(yVelocity)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                scrollBy(-(event.y - dy).toInt())
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun keepScroll(yVelocity: Float) {
        if (abs(yVelocity) >= 6000f) {
            ViewCompat.postOnAnimation(this) {
                scroller.fling(0, scrollY, 0, -yVelocity.toInt(), Int.MIN_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MAX_VALUE)
                invalidate()
            }
        }
    }

    override fun computeScroll() {
        L.d("computeScroll")
        if (scroller.computeScrollOffset()) {
            L.d("scroller.currY${scroller.currY}")
            scrollTo(scroller.currY)
            postInvalidate()
        }
    }

    private fun wordLength(word: String): Float {
        return paint.measureText(word)
    }

    private fun wordHeight(): Float {
        return paint.descent() - paint.ascent()
    }

    private fun scrollTo(scroll: Int) {
        scrollTo(0, scroll)

        if (scroll > lineHeight * 30) {
            scrollTo(0, lineHeight * 30)
            return
        }
        if (scroll < 0) {
            scrollTo(0, 0)
            return
        }
    }

    private fun scrollBy(scroll: Int) {
        scrollBy(0, scroll)

        if (scrollY > lineHeight * 30) {
            scrollTo(0, lineHeight * 30)
            return
        }
        if (scrollY < 0) {
            scrollTo(0, 0)
            return
        }
    }

    fun init(data: List<T>, lis: (T) -> Unit): PickerWheel<T> {
        this.data = data
        this.lis = lis
        return this
    }

    fun out() {
        lis(data[scrollY / lineHeight])
        obtain.recycle()
    }
}