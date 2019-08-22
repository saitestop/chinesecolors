package com.willxing.chinesecolors.custom

import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.util.Log
import android.graphics.Picture
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.RectF






class ListCView : View {

    var mPaint: Paint = Paint()
    // 圆心坐标,当前View的中心
    var xForCircle: Float = 0f;
    var yForCircle1: Float = 0f;
    var yForCircle2: Float = 0f;
    var yForCircle3: Float = 0f;
    var yForCircle4: Float = 0f;
    var yForCircle5: Float = 0f;
    var yForCircle6: Float = 0f;
    var yForCircle7: Float = 0f;
    var yForCircle8: Float = 0f;
    //圆环的宽度默认为半径的1／2
    var mRingWidth = 0f
    //如果未设置半径，则半径的值为view的宽、高一半的较小值
    var mRadius: Float = 0f

    var portrait = true

    constructor(mContext: Context) : super(mContext) {
        initPaint()
        checkOrientation()
    }

    constructor(mContext: Context, mAttributeSet: AttributeSet) : super(mContext, mAttributeSet) {
        initPaint()
        checkOrientation()
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
        setMeasuredDimension(widthSpecSize, heightSpecSize)

    }

    fun checkOrientation(){
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            portrait = true
        } else {
            //横屏
            portrait = false
        }
    }

    fun initCircle() {
//        Log.i("will","initCircle === " +width)
        xForCircle = width / 2f
        yForCircle1 = xForCircle * 1.5f
        yForCircle2 = xForCircle * 4.5f
        yForCircle3 = xForCircle * 7.5f
        yForCircle4 = xForCircle * 10.5f

        yForCircle5 = xForCircle * 13.5f
        yForCircle6 = xForCircle * 16f
        yForCircle7 = xForCircle * 18.5f
        yForCircle8 = xForCircle * 21f

        mRadius = width / 2f
        mRingWidth = mRadius / 10f
        mRadius = mRadius - mRingWidth

        mPaint.setStrokeWidth(mRingWidth);
    }


    override fun onDraw(canvas: Canvas?) {

        drawCircle(canvas)
        drawColorHead(canvas)
        drawCMYKText(canvas)
    }

    fun drawColorHead(canvas: Canvas?) {
        mPaint.setColor(Color.parseColor("#CBCBCB"))
        mPaint.setStrokeWidth(mRingWidth / 2);
        canvas?.drawLine(0f, mRingWidth, width.toFloat(), mRingWidth, mPaint);
        canvas?.drawLine(0f, yForCircle2 - yForCircle1, width.toFloat(), yForCircle2 - yForCircle1, mPaint);
        canvas?.drawLine(0f, yForCircle3 - yForCircle1, width.toFloat(), yForCircle3 - yForCircle1, mPaint);
        canvas?.drawLine(0f, yForCircle4 - yForCircle1, width.toFloat(), yForCircle4 - yForCircle1, mPaint);

        canvas?.drawLine(0f, yForCircle5 - yForCircle1, width.toFloat(), yForCircle5 - yForCircle1, mPaint);
        canvas?.drawLine(0f, yForCircle6 - yForCircle1, width.toFloat(), yForCircle6 - yForCircle1, mPaint);
        canvas?.drawLine(0f, yForCircle7 - yForCircle1, width.toFloat(), yForCircle7 - yForCircle1, mPaint);
        canvas?.drawLine(0f, yForCircle8 - yForCircle1, width.toFloat(), yForCircle8 - yForCircle1, mPaint);
    }

    fun drawCMYKText(canvas: Canvas?) {
        mPaint.setColor(Color.parseColor("#CBCBCB"))
        mPaint.setStrokeWidth(1f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(xForCircle / 2);
        var offset = xForCircle / 2
        canvas?.drawText("C", 0f, offset, mPaint)
        canvas?.drawText("M", 0f, yForCircle2 - yForCircle1 + offset, mPaint)
        canvas?.drawText("Y", 0f, yForCircle3 - yForCircle1 + offset, mPaint)
        canvas?.drawText("K", 0f, yForCircle4 - yForCircle1 + offset, mPaint)

        canvas?.drawText("R", 0f, yForCircle5 - yForCircle1 + offset, mPaint)
        canvas?.drawText("G", 0f, yForCircle6 - yForCircle1 + offset, mPaint)
        canvas?.drawText("B", 0f, yForCircle7 - yForCircle1 + offset, mPaint)


        mPaint.setTextSize(xForCircle);

        mPaint.setTextAlign(Paint.Align.CENTER);
        val fontMetrics = mPaint.fontMetrics
        val textHeight = (-fontMetrics.ascent - fontMetrics.descent) / 2
        mPaint.setColor(Color.BLUE);
        canvas?.drawText(c.toString(), xForCircle, yForCircle1 + textHeight, mPaint)
        mPaint.setColor(Color.RED);
        canvas?.drawText(m.toString(), xForCircle, yForCircle2 + textHeight, mPaint)
        mPaint.setColor(Color.YELLOW);
        canvas?.drawText(y.toString(), xForCircle, yForCircle3 + textHeight, mPaint)
        mPaint.setColor(Color.BLACK);
        canvas?.drawText(k.toString(), xForCircle, yForCircle4 + textHeight, mPaint)

        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.LEFT);
//        canvas?.drawText(r.toString(), 0f, yForCircle5 + textHeight, mPaint)
//        canvas?.drawText(g.toString(), 0f, yForCircle6 + textHeight, mPaint)
//        canvas?.drawText(b.toString(), 0f, yForCircle7 + textHeight, mPaint)

//        canvas?.drawRect(0f, mRingWidth, width.toFloat(),mRingWidth, mPaint);
//        canvas?.drawLine(0f, yForCircle2 - yForCircle1 , width.toFloat(),yForCircle2-yForCircle1, mPaint);
//        canvas?.drawLine(0f, yForCircle3-yForCircle1, width.toFloat(),yForCircle3-yForCircle1, mPaint);
//        canvas?.drawLine(0f, yForCircle4-yForCircle1, width.toFloat(),yForCircle4-yForCircle1, mPaint);
    }

    fun drawCircle(canvas: Canvas?) {
        mPaint.setColor(Color.parseColor("#CBCBCB"));// 背景
        mPaint.setStyle(Paint.Style.STROKE);
        initCircle()
        // 底环
//        Log.i("will"," " +xForCircle+" " + yForCircle1+" " + mRadius)
        canvas?.drawCircle(this.xForCircle, this.yForCircle1, mRadius, mPaint);
        canvas?.drawCircle(this.xForCircle, yForCircle2, mRadius, mPaint);
        canvas?.drawCircle(this.xForCircle, yForCircle3, mRadius, mPaint);
        canvas?.drawCircle(this.xForCircle, yForCircle4, mRadius, mPaint);


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setColor(Color.BLUE);
        val oval1 = RectF(mRingWidth, yForCircle1 - mRadius, xForCircle * 2 - mRingWidth, yForCircle1 + mRadius)
        canvas?.drawArc(oval1, -90f, 360f / 100 * c, false, mPaint);
        mPaint.setColor(Color.RED);
        val oval2 = RectF(mRingWidth, yForCircle2 - mRadius, xForCircle * 2 - mRingWidth, yForCircle2 + mRadius)
        canvas?.drawArc(oval2, -90f, 360f / 100 * m, false, mPaint);
        mPaint.setColor(Color.YELLOW);
        val oval3 = RectF(mRingWidth, yForCircle3 - mRadius, xForCircle * 2 - mRingWidth, yForCircle3 + mRadius)
        canvas?.drawArc(oval3, -90f, 360f / 100 * y, false, mPaint);
        mPaint.setColor(Color.BLACK);
        val oval4 = RectF(mRingWidth, yForCircle4 - mRadius, xForCircle * 2 - mRingWidth, yForCircle4 + mRadius)
        canvas?.drawArc(oval4, -90f, 360f / 100 * k, false, mPaint);
    }


    fun drawInPicture(canvas:Canvas?){

        canvas?.translate(0f,yForCircle5)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        canvas?.drawRect(0f, 0f, width.toFloat(), 50f, paint);



    }


    var c = 0
    var m = 0
    var y = 0
    var k = 0
    fun setCMYK(ints: IntArray) {
        c = ints[0]
        m = ints[1]
        y = ints[2]
        k = ints[3]
        invalidate()
    }

    fun getCMYK():IntArray{
        var intArray = IntArray(4)
        intArray[0]=c
        intArray[1]=m
        intArray[2]=y
        intArray[3]=k
      return intArray
    }

    lateinit var textRGB: String
    var r = 1
    var g = 1
    var b = 1

    fun setRGB(ints: IntArray) {
        r = ints[0]
        g = ints[1]
        b = ints[2]
        invalidate()
        Log.i("will", "r = " + r + "g = " + g + "b = " + b)
    }

//    fun settextRGB(textRGB:String){
//        this.textRGB = textRGB
//        var color:Int = textRGB.toInt(16);
//        r = 0xFF and color
//        g = 0xFF00 and color
//        g = g shr 8
//        b = 0xFF0000 and color
//        b = b shr 16
//        Log.i("will","r = " + r+"g = " + g+"b = " + b)
//        invalidate()
//    }


}
