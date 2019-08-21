package com.willxing.chinesecolors.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class ScrollTextView : View {
    private val text: String = "0123456789012"
    private  val mPaint: Paint = Paint()


    var offsets:FloatArray = FloatArray(3)
    var offsetStep = 0f;
    constructor(mContext: Context) : super(mContext) {
        initPaint()
    }




    constructor(mContext: Context, mAttributeSet: AttributeSet) : super(mContext, mAttributeSet){
        initPaint()
    }

    fun initPaint() {
        mPaint.setAntiAlias(true);// 抗锯齿效果
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);// 背景
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
//        val mLayoutSize = Math.min(widthSpecSize, heightSpecSize)
//        setMeasuredDimension(mLayoutSize, mLayoutSize)
        setMeasuredDimension(widthSpecSize,heightSpecSize)
    }




    override fun onDraw(canvas: Canvas?) {
//       offsets = offsetsInitByNum()
        drawText(canvas)
    }
   var textHeight = 0f
    fun offsetsInitByNum(number:Int):FloatArray{
        mPaint.textSize =  height.toFloat();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.LEFT);
        val fontMetrics = mPaint.fontMetrics
//         textHeight = (-fontMetrics.top - fontMetrics.bottom)
        textHeight =height.toFloat()
        var offsetstmp =FloatArray(3)
        var tmp:Int = number %1000
        offsetstmp[2] = (tmp /100).toInt()*textHeight.toFloat()
        tmp = tmp %100
        offsetstmp[1] = (tmp /10).toInt()*textHeight.toFloat()
        tmp = tmp %10
        offsetstmp[0] = (tmp).toInt()*textHeight.toFloat()
        return offsetstmp
    }

    fun drawText(canvas:Canvas?){
        offsetStep = textHeight
        if(text==null){
            return
        }
        if(offsets[0] >= textHeight*10){
            offsets[0] = 0f
        }

        if(offsets[1] >= textHeight*10){
            offsets[1] = 0f
        }

        if(offsets[2] >= textHeight*10){
            offsets[2] = 0f
        }
        var Descent = height/8f
        for(i in 0..text.length-1){
            canvas?.drawText(text.get(i).toString(),textHeight/7*8,textHeight*(i+1)-offsets[0] - Descent,mPaint)
        }
        for(i in 0..text.length-1){
            canvas?.drawText(text.get(i).toString(),textHeight/7*4,textHeight*(i+1)-offsets[1] - Descent,mPaint)
        }
        for(i in 0..text.length-1){
            canvas?.drawText(text.get(i).toString(),0f,textHeight*(i+1)-offsets[2] -Descent,mPaint)
        }

//        if(text.length>0)
//        canvas?.drawText(text.get(0).toString(),0f,textHeight,mPaint)
//        if(text.length>1)
//        canvas?.drawText(text.get(1).toString(),0f,textHeight*2,mPaint)
//        if(text.length>2)
//        canvas?.drawText(text.get(2).toString(),0f,textHeight*3,mPaint)
//        if(text.length>3)
//        canvas?.drawText(text.get(3).toString(),0f,textHeight*4,mPaint)

    }



}
