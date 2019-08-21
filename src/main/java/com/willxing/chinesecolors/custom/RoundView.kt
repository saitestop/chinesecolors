package com.willxing.chinesecolors.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.util.Log


class RoundView : View {

    var scrollState = 0

    var mPaint: Paint = Paint()
    val ringColorPaint = Paint()
    val textPaint = Paint()
    val paintPinYin = Paint()
    val paintRGBLine = Paint()


    var paths = Path()


    var text:String =""
    var textPinyin:String =""
    var textRGB:String =""


    // 圆心坐标,当前View的中心
    var xForCircle:Float = 0f;
    var yForCircle1:Float = 0f;
    var yForCircle2:Float = 0f;
    var yForCircle3:Float = 0f;
    var yForCircle4:Float = 0f;
    //圆环的宽度默认为半径的1／2
    var mRingWidth = 0f
    //如果未设置半径，则半径的值为view的宽、高一半的较小值
    var mRadius:Float =  0f

    var xForColorName:Float = 0f;
    var yForColorName1:Float = 0f;
    var yForColorName2:Float = 0f;
    var yForColorName3:Float = 0f;
    var yForColorName4:Float = 0f;

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
//        val mLayoutSize = Math.min(widthSpecSize, heightSpecSize)
//        setMeasuredDimension(mLayoutSize, mLayoutSize)

        setMeasuredDimension(widthSpecSize,heightSpecSize)


    }

    fun initCircle(){
//        Log.i("will","initCircle === " +width)
        xForCircle = width/4f
        yForCircle1 = xForCircle + xForCircle/2f
        yForCircle2 = xForCircle*3+xForCircle/4
        yForCircle3 = xForCircle*5+xForCircle/4
        yForCircle4 = xForCircle*7+xForCircle/4

        mRadius = width/4f
        mRingWidth =  mRadius / 2f
        mRadius = mRadius - mRingWidth

        mPaint.setStrokeWidth(mRingWidth);
    }

    fun initColorName(){
        textPaint.setStrokeWidth(1f);
        textPaint.setColor(Color.parseColor("#CBCBCB"));// 背景
        textPaint.textSize =  width/2.5f;
        textPaint.setStyle(Paint.Style.FILL);
        val fontMetrics = textPaint.fontMetrics

        xForColorName = width/2f
        yForColorName1 = - fontMetrics.top  + width/8f
        yForColorName2 = - fontMetrics.top*2+width/8f
        yForColorName3 = - fontMetrics.top*3+width/8f
        yForColorName4 = - fontMetrics.top*4+width/8f

    }

    override fun onDraw(canvas: Canvas?) {
        drawColorHead(canvas)
        if(scrollState ==0) {
            drawCMYK(canvas);
        }
        drawColorName(canvas)
        drawColorNamePinYin(canvas)
        drawColorRGB(canvas)
        drawColorRGBLine(canvas)
    }
    fun drawColorHead(canvas:Canvas?){
        if(textRGB !=null && !"".equals(textRGB)) {
            mPaint.setColor(Color.parseColor( textRGB))
        }
        canvas?.drawRect(0f, 0f, width.toFloat(),mRadius/2f, mPaint);
    }
    fun drawCMYK(canvas:Canvas?){
        mPaint.setColor(Color.parseColor("#CBCBCB"));
        initCircle()
        initColorName()

        // 底环
//        Log.i("will"," " +xForCircle+" " + yForCircle1+" " + mRadius)
        canvas?.drawCircle(this.xForCircle, this.yForCircle1, mRadius, mPaint);
        canvas?.drawCircle(this.xForCircle, yForCircle2, mRadius, mPaint);
        canvas?.drawCircle(this.xForCircle, yForCircle3, mRadius, mPaint);
        canvas?.drawCircle(this.xForCircle, yForCircle4, mRadius, mPaint);


        ringColorPaint.setStyle(Paint.Style.STROKE);
        ringColorPaint.setStrokeWidth(mRingWidth);
        ringColorPaint.setColor(Color.parseColor("#5c2223"));// 背景
        val oval1 = RectF(mRingWidth, yForCircle1-mRadius, xForCircle*2-mRingWidth, yForCircle1+mRadius)
        canvas?.drawArc(oval1,-90f,360f/100*c,false,ringColorPaint);
        val oval2 = RectF(mRingWidth, yForCircle2-mRadius, xForCircle*2-mRingWidth, yForCircle2+mRadius)
        canvas?.drawArc(oval2,-90f,360f/100*m,false,ringColorPaint);
        val oval3 = RectF(mRingWidth, yForCircle3-mRadius, xForCircle*2-mRingWidth, yForCircle3+mRadius)
        canvas?.drawArc(oval3,-90f,360f/100*y,false,ringColorPaint);
        val oval4 = RectF(mRingWidth, yForCircle4-mRadius, xForCircle*2-mRingWidth, yForCircle4+mRadius)
        canvas?.drawArc(oval4,-90f,360f/100*k,false,ringColorPaint);
    }


    fun drawColorName(canvas:Canvas?){
        if(text==null){
            return
        }
        if(text.length>0)
        canvas?.drawText(text.get(0).toString(),xForColorName,yForColorName1,textPaint)
        if(text.length>1)
        canvas?.drawText(text.get(1).toString(),xForColorName,yForColorName2,textPaint)
        if(text.length>2)
        canvas?.drawText(text.get(2).toString(),xForColorName,yForColorName3,textPaint)
        if(text.length>3)
        canvas?.drawText(text.get(3).toString(),xForColorName,yForColorName4,textPaint)

//        canvas?.d("texttexttext".toCharArray(), paths, 0f, 0f, paint);

    }

    fun drawColorNamePinYin(canvas:Canvas?){

        paintPinYin.setStrokeWidth(4f);
        paintPinYin.setColor(Color.parseColor("#CBCBCB"));
        paths.reset()
        paths.moveTo(getWidth().toFloat()*0.7f, getWidth()*2.2f)
        paths.lineTo(getWidth().toFloat()*0.7f, height.toFloat())
//        canvas?.drawPath(paths, paint);
        paintPinYin.textSize = getWidth()/4f;
        paintPinYin.setStyle(Paint.Style.FILL);
        canvas?.drawTextOnPath(textPinyin.toUpperCase(), paths, 0f, 0f, paintPinYin);

    }

    fun drawColorRGB(canvas:Canvas?){

        paintPinYin.setStrokeWidth(4f);
        paintPinYin.setStyle(Paint.Style.STROKE);
        paintPinYin.setColor(Color.parseColor("#CBCBCB"));
        paths.reset()
        paths.moveTo(getWidth().toFloat()*0.1f, getWidth()*2.2f)
        paths.lineTo(getWidth().toFloat()*0.1f, height.toFloat())
//        canvas?.drawPath(paths, paint);
        paintPinYin.textSize = getWidth()/4f;
        paintPinYin.setStyle(Paint.Style.FILL);
        canvas?.drawTextOnPath(textRGB, paths, 0f, 0f, paintPinYin);

    }

    fun drawColorRGBLine(canvas:Canvas?){

        paintRGBLine.setStrokeWidth(4f);
        paintRGBLine.setStyle(Paint.Style.STROKE);
        paintRGBLine.setColor(Color.parseColor("#CBCBCB"));// 背景
        paths.reset()
        var lineStartY = getWidth()*2.2f
        paths.moveTo(getWidth().toFloat()*0.35f, lineStartY)
        paths.lineTo(getWidth().toFloat()*0.35f, height.toFloat())
//        canvas?.drawPath(paths, paintRGBLine);
//
//        paths.reset()
        paths.moveTo(getWidth().toFloat()*0.45f, lineStartY)
        paths.lineTo(getWidth().toFloat()*0.45f, height.toFloat())
//        canvas?.drawPath(paths, paintRGBLine);

        paths.moveTo(getWidth().toFloat()*0.55f, lineStartY)
        paths.lineTo(getWidth().toFloat()*0.55f, height.toFloat())
        canvas?.drawPath(paths, paintRGBLine);


        paintRGBLine.setColor(Color.parseColor("#5c2223"));
        paths.reset()
        lineStartY = getWidth()*2.2f
        paths.moveTo(getWidth().toFloat()*0.35f,lineStartY )
        paths.lineTo(getWidth().toFloat()*0.35f, lineStartY + (height.toFloat() - lineStartY)*(r/255f))
        paths.moveTo(getWidth().toFloat()*0.45f, lineStartY)
        paths.lineTo(getWidth().toFloat()*0.45f, lineStartY + (height.toFloat() - lineStartY)*(g/255f))
        paths.moveTo(getWidth().toFloat()*0.55f, lineStartY)
        paths.lineTo(getWidth().toFloat()*0.55f,lineStartY + (height.toFloat() - lineStartY)*(b/255f))
        canvas?.drawPath(paths, paintRGBLine);
    }


    var c=0
    var m=0
    var y=0
    var k=0
    fun setCMYK(ints: IntArray){
        c=ints[0]
        m=ints[1]
        y=ints[2]
        k=ints[3]

    }


    var r=1
    var g=1
    var b=1

    fun setRGB(ints:IntArray){
        r = ints[0]
        g = ints[1]
        b =ints[2]
        Log.i("will","r = " + r+"g = " + g+"b = " + b)
    }

//    fun RGB2INT(textRGB:String){
//      var color:Int = textRGB.toInt(16);
//       r = 0xFF and color
//       g = 0xFF00 and color
//       g = g shr 8
//       b = 0xFF0000 and color
//       b = b shr 16
//      Log.i("will","r = " + r+"g = " + g+"b = " + b)
//    }

}
