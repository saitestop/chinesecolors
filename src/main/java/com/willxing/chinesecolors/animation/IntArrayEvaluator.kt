package com.willxing.chinesecolors.animation

import android.animation.TypeEvaluator

class IntArrayEvaluator: TypeEvaluator<IntArray>{
     private lateinit  var intArray:IntArray
    override fun evaluate(fraction: Float, startValue: IntArray?, endValue: IntArray?): IntArray {
        if(startValue!=null ){
            intArray = IntArray(startValue.size)
        }

        if(startValue!=null && endValue!=null) {
            for (i in 0..startValue.size - 1) {
                intArray[i] = startValue[i] + (fraction * (endValue[i] - startValue[i])).toInt();
            }
            return intArray
        }
        return intArray
    }
}