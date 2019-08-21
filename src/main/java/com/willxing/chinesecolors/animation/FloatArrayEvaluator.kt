package com.willxing.chinesecolors.animation

import android.animation.TypeEvaluator

class FloatArrayEvaluator: TypeEvaluator<FloatArray>{
    private lateinit  var floatArray: FloatArray
    override fun evaluate(fraction: Float, startValue: FloatArray?, endValue: FloatArray?): FloatArray {
        if(startValue!=null ){
            floatArray = FloatArray(startValue.size)
        }
        if(startValue!=null && endValue!=null) {
            for (i in 0..startValue.size - 1) {
                floatArray[i] = startValue[i] + (fraction * (endValue[i] - startValue[i]));
            }
            return floatArray
        }else{
            return floatArray!!
        }

    }
}