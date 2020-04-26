package com.open.dqmvvm.v

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.open.dqmvvm.R
import com.open.dqmvvm.log.L

class Labels : View {

    private var labelColors = mutableListOf<Int>()
    private var textColor: Int = 0
    private var paint: Paint = Paint()
    private var labelPadding = 20f
    private var viewPadding: Float = 20f
    private var space: Float = 20f
    private lateinit var labels :List<String>

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        paint.isAntiAlias = true

        val atts = context?.obtainStyledAttributes(attrs, R.styleable.Labels)
        paint.textSize = atts?.getFloat(R.styleable.Labels_textSize, 20f)!!
        L.d("paint.textSize${paint.textSize}")
        labelColors.add(resources.getColor(atts.getResourceId(R.styleable.Labels_labelColor, R.color.colorPrimary), null))
        labelColors.add(resources.getColor(atts.getResourceId(R.styleable.Labels_labelColor2, R.color.colorPrimary), null))
        L.d("paint.labelColors${labelColors}")
        textColor = resources.getColor(atts.getResourceId(R.styleable.Labels_textColor, R.color.colorPrimary), null)
        L.d("paint.textColor${textColor}")
        labelPadding = atts.getFloat(R.styleable.Labels_labelPadding, 30f)
        L.d("paint.labelPadding${labelPadding}")
        viewPadding = atts.getFloat(R.styleable.Labels_viewPadding, 30f)
        L.d("paint.viewPadding${viewPadding}")
        space = atts.getFloat(R.styleable.Labels_space, 30f)
        L.d("paint.space${space}")
        atts.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //xml中宽高
        val xmlwdi = layoutParams.width
        val xmlhei = layoutParams.height
        //父容器规格
        val parwdi = MeasureSpec.getSize(widthMeasureSpec)
        L.d("onMeasure.parwdi$parwdi")
        //真實內容寬高
        var lineWdith = 0f
        val lineHeight = paint.descent() - paint.ascent() + 2 * viewPadding
        var lines = 1
        for (word in labels) {
            lineWdith += wordLength(word)
            lineWdith += 2 * labelPadding
            lineWdith += space
            if (lineWdith >= parwdi - viewPadding * 2 + space) {
                lineWdith = 0f
                lines++
            }
        }
        val conwdi = if (lines >= 1) {
            parwdi.toFloat()
        } else {
            lineWdith - space + viewPadding * 2
        }
        val conhei = lineHeight * lines + 2 * labelPadding
        if (xmlhei == ViewGroup.LayoutParams.WRAP_CONTENT && xmlwdi == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(conwdi.toInt(), conhei.toInt())
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var start = 0f + viewPadding
        var preStart = 0f + viewPadding
        val lineHeight = paint.descent() - paint.ascent() + 2 * labelPadding
        var lines = 1
        for ((n, word) in labels.withIndex()) {
            val wordLength = wordLength(word)
            preStart += wordLength
            preStart += 2 * labelPadding
            preStart += space
            if (preStart >= width) {
                lines++
                start = 0f + viewPadding
                L.d("paint.lines${lines}")
            }
            paint.color = labelColors[n % labelColors.size]
            canvas?.drawRoundRect(start, viewPadding * lines + lineHeight * (lines - 1), start + wordLength + 2 * labelPadding, lineHeight * lines + viewPadding * lines, 10f, 10f, paint)
            paint.color = textColor
            canvas?.drawText(word, padding(start), padding(paint.textSize) + viewPadding * lines + lineHeight * (lines - 1), paint)
            start = preStart
        }
    }

    private fun padding(v: Float): Float {
        return v + labelPadding
    }

    private fun wordLength(word: String): Int {
        return paint.measureText(word).toInt()
    }

    fun initLabels(labels: List<String>) {
        this.labels = labels
    }

}