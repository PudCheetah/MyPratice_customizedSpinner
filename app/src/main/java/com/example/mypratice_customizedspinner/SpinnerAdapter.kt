package com.example.mypratice_customizedspinner

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mypratice_customizedspinner.databinding.SpinnerCollapsedItemViewBinding
import com.example.mypratice_customizedspinner.databinding.SpinnerDropdownItemViewBinding

// 自定義 Adapter，繼承自 ArrayAdapter
class SpinnerAdapter(context: Context, data: ArrayList<SpinnerItemDataClass>):
    // 呼叫父類別建構式，傳入佈局及資料
    ArrayAdapter<SpinnerItemDataClass>(context, R.layout.spinner_dropdown_item_view, data) {
        private val TAG = "MyTag" + SpinnerAdapter::class.java.simpleName
    private lateinit var binding_dropdown: SpinnerDropdownItemViewBinding
    private lateinit var binding_collapsed: SpinnerCollapsedItemViewBinding

    // 統一處理視圖的建立與資料綁定
    fun createDropdownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 用於持有列表項的視圖物件
            val view: View

            // 判斷是否為可回收的 View，這是 Adapter 效能優化的基礎
            if(convertView == null){
                // 若無，則加載新佈局
                binding_dropdown = SpinnerDropdownItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
                view = binding_dropdown.root
            }else{
                // 若有，則直接重複使用
                view = convertView
                // 從回收的 View 重新綁定 binding 物件
                binding_dropdown = SpinnerDropdownItemViewBinding.bind(convertView)
            }

            // 獲取目前位置的資料
            val data = getItem(position)

            // 將資料綁定到 View 元件上
            if(data != null){
                binding_dropdown.tvTag.text = data.name
                binding_dropdown.tvMessage.text = data.message
                binding_dropdown.imageView.setImageResource(data.icon)
            }
            // 回傳處理完成的 View
            return view
        }
    fun createCollapsedView(position: Int, convertView: View?, parent: ViewGroup): View{
        // 用於持有列表項的視圖物件
        val view: View

        // 判斷是否為可回收的 View，這是 Adapter 效能優化的基礎
        if(convertView == null){
            // 若無，則加載新佈局
            binding_collapsed = SpinnerCollapsedItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding_collapsed.root
        }else{
            // 若有，則直接重複使用
            view = convertView
            // 從回收的 View 重新綁定 binding 物件
            binding_collapsed = SpinnerCollapsedItemViewBinding.bind(convertView)
        }

        // 獲取目前位置的資料
        val data = getItem(position)

        // 將資料綁定到 View 元件上
        if(data != null){
            binding_collapsed.tvTag.text = data.name
            binding_collapsed.imageView.setImageResource(data.icon)
        }
        // 回傳處理完成的 View
        return view
    }

    // 複寫 getView，決定 Spinner 未展開時和目前已選項目的顯示樣式
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getView(): ${position}")
        return createCollapsedView(position, convertView, parent)
    }

    // 複寫 getDropDownView，決定 Spinner 下拉選單中每個項目的樣式
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        Log.d(TAG, "getDropDownView(): ${position}")
        return createDropdownView(position, convertView, parent)
    }
}
