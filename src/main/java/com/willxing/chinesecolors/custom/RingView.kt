package com.willxing.chinesecolors.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class RingView : View {
    private  val mPaint: Paint = Paint()
    var tip = "C"
    var value =56

    constructor(mContext: Context) : super(mContext) {
        initPaint()
    }
    constructor(mContext: Context, mAttributeSet: AttributeSet) : super(mContext, mAttributeSet){
        initPaint()
    }

    fun initPaint() {
        mPaint.setAntiAlias(true);// 抗锯齿效果
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#CBCBCB"));// 背景
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthSpecSize,heightSpecSize)
    }




    override fun onDraw(canvas: Canvas?) {
        drawCircle(canvas)
        drawColorHead(canvas)
        drawCMYKText(canvas)
    }


    // 圆心坐标,当前View的中心
    var xForCircle: Float = 0f;
    var yForCircle1: Float = 0f;

    //圆环的宽度默认为半径的1／2
    var mRingWidth = 0f
    //如果未设置半径，则半径的值为view的宽、高一半的较小值
    var mRadius: Float = 0f

    fun drawCircle(canvas: Canvas?) {
        mPaint.setColor(Color.parseColor("#CBCBCB"));// 背景
        mPaint.setStyle(Paint.Style.STROKE);
        initCircle()
        // 底环
        canvas?.drawCircle(this.xForCircle, this.yForCircle1, mRadius, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setColor(Color.BLUE);
        val oval1 = RectF(mRingWidth, yForCircle1 - mRadius, xForCircle * 2 - mRingWidth, yForCircle1 + mRadius)
        canvas?.drawArc(oval1, -90f, 360f / 100 * value, false, mPaint);
    }

    fun initCircle() {
//        Log.i("will","initCircle === " +width)
        xForCircle = width / 2f
        yForCircle1 = xForCircle * 1.5f

        mRadius = width / 2f
        mRingWidth = mRadius / 10f
        mRadius = mRadius - mRingWidth
        mPaint.setStrokeWidth(mRingWidth);
    }

    fun drawColorHead(canvas: Canvas?) {
        mPaint.setColor(Color.parseColor("#CBCBCB"))
        mPaint.setStrokeWidth(mRingWidth / 2);
        canvas?.drawLine(0f, mRingWidth, width.toFloat(), mRingWidth, mPaint);
    }

    fun drawCMYKText(canvas: Canvas?) {
        mPaint.setColor(Color.parseColor("#CBCBCB"))
        mPaint.setStrokeWidth(1f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(xForCircle / 2);
        var offset = xForCircle / 2
        canvas?.drawText(tip, 0f, offset, mPaint)


        mPaint.setTextSize(xForCircle);

        mPaint.setTextAlign(Paint.Align.CENTER);
        val fontMetrics = mPaint.fontMetrics
        val textHeight = (-fontMetrics.ascent - fontMetrics.descent) / 2
        mPaint.setColor(Color.BLUE);
        canvas?.drawText(value.toString(), xForCircle, yForCircle1 + textHeight, mPaint)
    }
}
