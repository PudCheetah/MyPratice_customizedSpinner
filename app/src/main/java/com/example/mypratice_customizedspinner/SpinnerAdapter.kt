package com.example.mypratice_customizedspinner

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.viewbinding.ViewBinding
import com.example.mypratice_customizedspinner.databinding.SpinnerCollapsedItemViewBinding
import com.example.mypratice_customizedspinner.databinding.SpinnerDropdownItemViewBinding

// 自定義 Adapter，繼承自 ArrayAdapter
class SpinnerAdapter(context: Context, data: ArrayList<SpinnerItemDataClass>) :
    // 呼叫父類別建構式，由於我們手動處理所有 View，故 resource ID 傳 0，避免混淆
    ArrayAdapter<SpinnerItemDataClass>(context, 0, data) {

    private val TAG = "MyTag" + SpinnerAdapter::class.java.simpleName

    /**
     * @param B 泛型，使用 reified 關鍵字，使其類型在運行時得以保留，從而可以進行安全的類型檢查。
     * @param convertView 可回收的舊 View。
     * @param parent 父 ViewGroup。
     * @param inflater 一個 Lambda 函式，用於加載對應的 Binding。
     * @return 回傳一個 Pair，包含最終的 View 和與之對應的 Binding 物件。
     */
    private inline fun <reified B : ViewBinding> createOrReuseView(
        convertView: View?,
        parent: ViewGroup,
        inflater: (LayoutInflater, ViewGroup, Boolean) -> B
    ): Pair<View, B> {
        // 檢查 convertView 的 tag 是否為我們期望的 Binding 類型
        val holder = convertView?.tag
        if (holder is B) {
            // 如果是，代表 View 可以被安全地重複使用，直接返回即可。
            // 這裡因為進行了 `is B` 的檢查，Kotlin 可以智能轉型(smart cast)，無需不安全的 `as`
            return convertView!! to holder
        }

        // 如果 convertView 是 null，或是它的 tag 類型不對（不太可能發生，但這是更安全的寫法）
        // 則創建一個全新的 View 和 Binding
        val binding = inflater(LayoutInflater.from(context), parent, false)
        val view = binding.root
        view.tag = binding
        return view to binding
    }


    private fun createDropdownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 一行程式碼即可獲取 View 和 Binding，邏輯被抽離到通用函式中
        val (view, binding) = createOrReuseView(convertView, parent, SpinnerDropdownItemViewBinding::inflate)

        // 獲取目前位置的資料
        getItem(position)?.let { data ->
            // 將資料綁定到 View 元件上
            binding.tvTag.text = data.name
            binding.tvMessage.text = data.message
            binding.imageView.setImageResource(data.icon)
        }
        return view
    }


    private fun createCollapsedView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 同樣，一行程式碼即可獲取 View 和 Binding
        val (view, binding) = createOrReuseView(convertView, parent, SpinnerCollapsedItemViewBinding::inflate)

        // 獲取目前位置的資料
        getItem(position)?.let { data ->
            // 將資料綁定到 View 元件上
            binding.tvTag.text = data.name
            binding.imageView.setImageResource(data.icon)
        }
        return view
    }

    // 複寫 getView，決定 Spinner 未展開時和目前已選項目的顯示樣式
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getView(): $position")
        return createCollapsedView(position, convertView, parent)
    }

    // 複寫 getDropDownView，決定 Spinner 下拉選單中每個項目的樣式
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getDropDownView(): $position")
        return createDropdownView(position, convertView, parent)
    }
}
