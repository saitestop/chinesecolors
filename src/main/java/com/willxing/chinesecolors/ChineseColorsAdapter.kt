package com.willxing.chinesecolors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.willxing.chinesecolors.bean.ColorData
import com.willxing.chinesecolors.custom.RoundView

class ChineseColorsAdapter(private val context:Context,private val data:List<ColorData>): RecyclerView.Adapter<ChineseColorsAdapter.ColorViewHolder>() {
    override fun getItemCount(): Int {
        return data.size
    }
   lateinit var listener:OnItemClickListener ;
    var   state:Int = 0



    override fun onBindViewHolder(colorViewHolder: ColorViewHolder, index: Int) {
        val layout =colorViewHolder.layout as ConstraintLayout
        var colorContent = layout.findViewById<RoundView>(R.id.color_content)
        colorContent.text = data.get(index).name;
        colorContent.textPinyin = data.get(index).pinyin;
        colorContent.textRGB =  data.get(index).hex
        colorContent.setRGB(data.get(index).RGB)
        colorContent.setCMYK(data.get(index).CMYK)
        colorViewHolder.onClickListener.index =index;

//        colorContent.scrollState = state



//        var colorContent = layout.findViewById<TextView>(R.id.color_content)
//        colorContent.text = data.get(index).colorName;


//        colorContent.setOnClickListener(colorViewHolder.onClickListener)
//        layout.setOnClickListener(object:View.OnClickListener {
//            override fun onClick(v: View?) {
//                listener.onClickListerner(index);
//            }
//        })



    }



    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ColorViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.color_item, parent, false) as ConstraintLayout

        val onClickListener = AdapterOnClickListener(listener,0);
        layout.setOnClickListener(onClickListener)
        return ColorViewHolder(layout,onClickListener)
    }

    class ColorViewHolder(val layout: ConstraintLayout,val onClickListener: AdapterOnClickListener) : RecyclerView.ViewHolder(layout)

    interface  OnItemClickListener{
       fun onClickListerner(index: Int);
   }

     class AdapterOnClickListener(var listener:OnItemClickListener,var index: Int):View.OnClickListener{

        override fun onClick(v: View?) {
            listener.onClickListerner(index)
        }
    }

}