package com.willxing.chinesecolors

import android.animation.*
import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Configuration
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willxing.chinesecolors.animation.FloatArrayEvaluator
import com.willxing.chinesecolors.animation.IntArrayEvaluator
import com.willxing.chinesecolors.bean.ColorData
import com.willxing.chinesecolors.custom.ListCView
import com.willxing.chinesecolors.custom.ScrollTextView
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ChineseColorsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var listCView: ListCView
    private lateinit var layout: ConstraintLayout
    private lateinit var select_color_text: TextView
    private lateinit var  select_color_pinyin_text:TextView
    private lateinit var  scrollTextView_r:ScrollTextView
    private lateinit var  scrollTextView_g:ScrollTextView
    private lateinit var  scrollTextView_b:ScrollTextView

    var currentBackgroundColor = Color.BLACK
    var currentTextdColor = Color.BLACK
    var  CMYKCurrent:IntArray = IntArray(4)

    private val listColorData = ArrayList<ColorData>()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
             setContentView(R.layout.activity_main)
//         }else{
//             setContentView(R.layout.activity_main_landscape)
//         }
        initView()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
             setContentView(R.layout.activity_main)
         }else{
            setContentView(R.layout.activity_main_landscape)
         }
        initView()
    }

    private fun initView() {
        scrollTextView_r = findViewById(R.id.scrolltext_r)
//        scrollTextView_r.setTextColor(Color.RED)
        scrollTextView_g = findViewById(R.id.scrolltext_g)
//        scrollTextView_g.setTextColor(Color.GREEN)
        scrollTextView_b =  findViewById(R.id.scrolltext_b)
//        scrollTextView_b.setTextColor(Color.BLUE)

        select_color_text = findViewById(R.id.select_color_text)
        select_color_pinyin_text =findViewById(R.id.select_color_pinyin_text)
        listCView = findViewById(R.id.listCView)
        layout =  findViewById<ConstraintLayout>(R.id.chineseColorLayout)

        viewManager = GridLayoutManager (this,3)

        viewAdapter = ChineseColorsAdapter(applicationContext,listColorData)
//        recyclerViewForList.ada
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewForList).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
        recyclerView.addOnScrollListener( object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.i("will","newState = " + newState)
                viewAdapter.state = newState
            }
        })


        ReadDateAsyncTask(assets,viewAdapter,listColorData).execute()

        viewAdapter.listener = object : ChineseColorsAdapter.OnItemClickListener {
            override fun onClickListerner(index:Int) {
                val item: ColorData = listColorData.get(index);
                select_color_text.setText(item.name)
                select_color_pinyin_text.setText(item.pinyin.toUpperCase())
                listCView.textRGB = item.hex
                listCView.setRGB(item.RGB)

                initAnimation(item)
            }
        }
    }


    fun initAnimation(item: ColorData){
        var animatorForCMYK =   AnimationForCMYK(listCView.getCMYK(),item.CMYK)
        var animatorForBG = AnimationForBackground(currentBackgroundColor,Color.parseColor(item.hex))
        var animatorForSelectText =  AnimationForText(currentTextdColor,Color.parseColor(item.hex),select_color_text)
        var animatorForSelectTextPinyin =  AnimationForText(currentTextdColor,Color.parseColor(item.hex),select_color_pinyin_text)
        var animatorForSBC =  AnimationForStatuBarColor(currentBackgroundColor,Color.parseColor(item.hex))

        var offsetNextr = scrollTextView_r.offsetsInitByNum(item.RGB[0])
        var offsetNextg = scrollTextView_g.offsetsInitByNum(item.RGB[1])
        var offsetNextb = scrollTextView_b.offsetsInitByNum(item.RGB[2])
        var animatorForST_r =     AnimationForScrollText(scrollTextView_r,scrollTextView_r.offsets,offsetNextr)
        var animatorForST_g =     AnimationForScrollText(scrollTextView_g,scrollTextView_g.offsets,offsetNextg)
        var animatorForST_b =     AnimationForScrollText(scrollTextView_b,scrollTextView_b.offsets,offsetNextb)

        var animationSet = AnimatorSet()
        animationSet.play(animatorForCMYK)
                .with(animatorForBG)
                .with(animatorForSBC)
                .with(animatorForST_r)
                .with(animatorForST_g)
                .with(animatorForST_b)
                .with(animatorForSelectText)
                .with(animatorForSelectTextPinyin)
        animationSet.start()
    }
    //设置状态栏颜色动画
    fun   AnimationForStatuBarColor(colorCurrent:Int,colorNext:Int):ValueAnimator{
        var window:Window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        var   colorAnim: ValueAnimator = ObjectAnimator.ofInt(window,"statusBarColor", colorCurrent, colorNext);
        colorAnim.setDuration(1000);
        colorAnim.setEvaluator(ArgbEvaluator());
        return colorAnim
    }

    fun AnimationForBackground(colorCurrent:Int,colorNext:Int):ValueAnimator{
      var   colorAnim: ValueAnimator = ObjectAnimator.ofInt(layout,"backgroundColor", colorCurrent, colorNext);
        colorAnim.setDuration(1000);
        colorAnim.setEvaluator(ArgbEvaluator());
        colorAnim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                currentBackgroundColor = colorNext;
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

        return  colorAnim
    }

    fun reverseColor(color:Int):Int{
        var red: Int = color shr 16 and 0x0ff
        var green: Int = color shr 8 and 0x0ff
        var blue: Int = color and 0x0ff

        red = 255 - red
        green = 255 - green
        blue = 255 - blue
        return Color.rgb(red, green, blue)
    }

    fun AnimationForText(colorCurrent:Int,colorNext:Int,item:TextView):ValueAnimator{
        var   colorAnim: ValueAnimator = ObjectAnimator.ofInt(item,"TextColor", colorCurrent, reverseColor (colorNext));
        colorAnim.setDuration(1000);
        colorAnim.setEvaluator(ArgbEvaluator());
        colorAnim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                currentTextdColor = reverseColor (colorNext);
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

        return  colorAnim
    }
    //自定义插值器
    fun  AnimationForCMYK(CMYKCurrent:IntArray,CMYKNext:IntArray):ValueAnimator{
        val valueAnimator = ValueAnimator()
        valueAnimator.setDuration(1000);
        valueAnimator.setObjectValues(CMYKCurrent,CMYKNext);//传入的参数
        valueAnimator.setInterpolator(LinearInterpolator());
        valueAnimator.setEvaluator(IntArrayEvaluator())

        valueAnimator.addUpdateListener(object :ValueAnimator.AnimatorUpdateListener
        {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                var intArray = animation?.getAnimatedValue()
                listCView.setCMYK(intArray as IntArray)
//                Log.i("will","c = " + intArray[0]+" m = " + intArray[1]+" y = " + intArray[2]+" k = " + intArray[3])
            }

        });
      return  valueAnimator
    }


    fun  AnimationForScrollText(scrollTextView: ScrollTextView,currentOffsets:FloatArray,nextOffsets:FloatArray):ValueAnimator{
        val valueAnimator = ValueAnimator()
        valueAnimator.setDuration(1000);
        valueAnimator.setObjectValues(currentOffsets,nextOffsets);//传入的参数
        valueAnimator.setInterpolator(LinearInterpolator());
        valueAnimator.setEvaluator(FloatArrayEvaluator())
        valueAnimator.addUpdateListener(object :ValueAnimator.AnimatorUpdateListener
        {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                var offset:FloatArray = animation?.getAnimatedValue() as FloatArray
                scrollTextView.offsets = offset
                scrollTextView.invalidate()
            }
        });
        return  valueAnimator
    }

    class ReadDateAsyncTask(private val assets: AssetManager,val viewAdapter: RecyclerView.Adapter<*>,val list2:ArrayList<ColorData>): AsyncTask<String, Int, Int>() {

        override fun doInBackground(vararg params: String?): Int {
//            readData()
            readData2()
            return 0
        }

        override fun onPostExecute(result: Int?) {
            if(result == 0){
                viewAdapter.notifyDataSetChanged()
            }
        }



        fun readData2() {
            val br:BufferedReader =    BufferedReader(InputStreamReader
            (assets.open("colors.json")))
            val jsonArray:JSONArray
            jsonArray = JSONArray( br.readText())
            for(i in 0..jsonArray.length()-1){
                val item:JSONObject =  jsonArray.getJSONObject(i);
                var cmyk :IntArray = JSONArrayTOIntArray(item.optJSONArray("CMYK"));
                var rgb :IntArray = JSONArrayTOIntArray(item.optJSONArray("RGB"));
                list2.add(ColorData(item.optString("name"), item.optString("pinyin"), item.optString("hex"),cmyk,rgb))
//                Log.i("will",list2.get(i).toString())
//                Log.i("will",list2.size.toString())
            }
            br.close();
        }

        fun   JSONArrayTOIntArray(jsonArray:JSONArray): IntArray{
            var cmyk :IntArray = IntArray(jsonArray.length())
            for(i in 0..jsonArray.length()-1){
                cmyk[i] = jsonArray.optInt(i)
            }
            return cmyk
        }
    }

}
