package com.example.mypratice_customizedspinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mypratice_customizedspinner.databinding.SpinnerItemViewBinding

// 自定義 Adapter，繼承自 ArrayAdapter
class SpinnerAdapter(context: Context, data: ArrayList<SpinnerItemDataClass>):
    // 呼叫父類別建構式，傳入佈局及資料
    ArrayAdapter<SpinnerItemDataClass>(context, R.layout.spinner_item_view, data) {
    private lateinit var binding: SpinnerItemViewBinding

    // 統一處理視圖的建立與資料綁定
    fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 用於持有列表項的視圖物件
            val view: View

            // 判斷是否為可回收的 View，這是 Adapter 效能優化的基礎
            if(convertView == null){
                // 若無，則加載新佈局
                binding = SpinnerItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
                view = binding.root
            }else{
                // 若有，則直接重複使用
                view = convertView
                // 從回收的 View 重新綁定 binding 物件
                binding = SpinnerItemViewBinding.bind(convertView)
            }

            // 獲取目前位置的資料模型
            val data = getItem(position)

            // 將資料綁定到 View 元件上
            if(data != null){
                binding.textView.text = data.name
                binding.imageView.setImageResource(data.icon)
            }
            // 回傳處理完成的 View
            return view
        }

    // 複寫 getView，決定 Spinner 未展開時的顯示樣式
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    // 複寫 getDropDownView，決定 Spinner 下拉選單中每個項目的樣式
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return createView(position, convertView, parent)
    }
}
