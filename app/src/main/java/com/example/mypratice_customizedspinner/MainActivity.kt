package com.example.mypratice_customizedspinner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mypratice_customizedspinner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 呼叫新的方法來設定 Adapter
        adapterSet_2()
        // adapterSet() // 您可以隨時換回舊的方法進行比較
    }

    /**
     * 方法一：資料來源寫死在 Kotlin 程式碼中
     */
    fun adapterSet(){
        val mSpinnerData = ArrayList<SpinnerItemDataClass>()
        mSpinnerData.clear() // 先清空舊資料
        mSpinnerData.add(SpinnerItemDataClass("A", "豹",  R.drawable.pic1))
        mSpinnerData.add(SpinnerItemDataClass("B", "豹豹", R.drawable.pic2))
        mSpinnerData.add(SpinnerItemDataClass("C", "豹豹豹", R.drawable.pic3))
        mSpinnerData.add(SpinnerItemDataClass("D", "豹豹豹豹", R.drawable.pic4))

        val adapter = SpinnerAdapter_advance(this, mSpinnerData)
        binding.spinMySpin.adapter = adapter
    }

    /**
     * 方法二：從 res/values/spinner_items.xml 讀取資料
     */
    fun adapterSet_2(){
        val mSpinnerData = ArrayList<SpinnerItemDataClass>()
        // 1. 從 XML 讀取文字陣列
        val names = resources.getStringArray(R.array.spinner_item_text)
        // 2. 從 XML 讀取圖片資源陣列
        val images = resources.obtainTypedArray(R.array.spinner_item_image)
        // 3. 從 XML 讀取文字資源陣列
        val messages = resources.getStringArray(R.array.spinner_item_message)
        // 4. 迴圈遍歷，將文字和圖片組合成 SpinnerItemDataClass 物件
        for (i in names.indices) {
            val name = names[i]
            // 使用 getResourceId 來獲取圖片的 ID，如果找不到則使用預設值 -1
            val imageResId = images.getResourceId(i, -1)
            val message = messages[i]
            if (imageResId != -1) { // 確保圖片 ID 有效
                mSpinnerData.add(SpinnerItemDataClass(name, message,imageResId))
            }
        }
        // 5. 釋放 TypedArray 資源，這是一個非常重要的步驟，能避免記憶體洩漏
        images.recycle()
        // 6. 建立並設定 Adapter
        val adapter = SpinnerAdapter_advance(this, mSpinnerData)
        binding.spinMySpin.adapter = adapter
    }
}
