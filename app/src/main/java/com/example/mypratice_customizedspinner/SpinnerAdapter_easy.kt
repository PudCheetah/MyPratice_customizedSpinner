package com.example.mypratice_customizedspinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mypratice_customizedspinner.databinding.SpinnerCollapsedItemViewBinding
import com.example.mypratice_customizedspinner.databinding.SpinnerDropdownItemViewBinding

// 自定義 Adapter，繼承自 ArrayAdapter（基本寫法）
// 這個版本未使用泛型輔助函數，而是將 ViewHolder 邏輯直接寫在每個 createView 方法中，
// 讓我們能清楚看到 ViewHolder 模式最原始的運作方式。
class SpinnerAdapter_easy(context: Context, data: ArrayList<SpinnerItemDataClass>) :
    // 呼叫父類別建構式，由於我們手動處理所有 View，故 resource ID 傳 0，避免混淆
    ArrayAdapter<SpinnerItemDataClass>(context, 0, data) {

    /**
     * 建立「收起狀態」的視圖
     * @return 返回一個僅顯示圖示和標籤的 View
     */
    fun createCollapsedView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SpinnerCollapsedItemViewBinding
        val view: View

        // 檢查是否有可回收的 View，並且其 tag 中儲存的是正確的 Binding 類型
        // 這是為了避免在多種類型 View 的列表中，因 View 回收錯亂導致的 ClassCastException
        if (convertView != null && convertView.tag is SpinnerCollapsedItemViewBinding) {
            // 如果條件滿足，直接重複使用 View 和之前存好的 Binding 物件
            view = convertView
            binding = convertView.tag as SpinnerCollapsedItemViewBinding
        } else {
            // 如果沒有可回收的 View，則加載新的佈局
            binding = SpinnerCollapsedItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            // 將新建立的 binding 物件存入 view.tag，以便這個 View 下次被回收時可以被重複使用
            view.tag = binding
        }
        // 獲取目前位置的資料
        getItem(position)?.let { data ->
            // 將資料綁定到 View 元件上
            binding.tvTag.text = data.name
            binding.imageView.setImageResource(data.icon)
        }
        return view
    }

    /**
     * 建立「下拉選單」中每個項目的視圖
     * @return 返回一個顯示完整資訊（圖示、標籤、訊息）的 View
     */
    fun createDropdownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SpinnerDropdownItemViewBinding
        val view: View

        // 檢查是否有可回收的 View，並且其 tag 中儲存的是正確的 Binding 類型
        // 這是為了避免在多種類型 View 的列表中，因 View 回收錯亂導致的 ClassCastException
        if (convertView != null && convertView.tag is SpinnerDropdownItemViewBinding) {
            // 如果條件滿足，直接重複使用 View 和之前存好的 Binding 物件
            view = convertView
            binding = convertView.tag as SpinnerDropdownItemViewBinding
        } else {
            binding = SpinnerDropdownItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            // 將新建立的 binding 物件存入 view.tag，以便這個 View 下次被回收時可以被重複使用
            view.tag = binding
        }

        // 獲取目前位置的資料
        getItem(position)?.let { data ->
            // 將資料綁定到 View 元件上
            binding.tvTag.text = data.name
            binding.tvMessage.text = data.message
            binding.imageView.setImageResource(data.icon)
        }
        return view
    }

    // 複寫 getView，決定 Spinner 在「未展開」時的顯示樣式
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCollapsedView(position, convertView, parent)
    }

    // 複寫 getDropDownView，決定 Spinner「下拉選單中」每個項目的樣式
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createDropdownView(position, convertView, parent)
    }
}
