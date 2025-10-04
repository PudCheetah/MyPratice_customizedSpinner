package com.example.mypratice_customizedspinner

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mypratice_customizedspinner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mSpinnerData = ArrayList<SpinnerItemDataClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapterSet()
    }
    fun adapterSet(){
        mSpinnerData.add(SpinnerItemDataClass("豹", R.drawable.pic1))
        mSpinnerData.add(SpinnerItemDataClass("豹豹", R.drawable.pic2))
        mSpinnerData.add(SpinnerItemDataClass("豹豹豹", R.drawable.pic3))
        mSpinnerData.add(SpinnerItemDataClass("豹豹豹豹", R.drawable.pic4))

        val adapter = SpinnerAdapter(this, mSpinnerData)

        binding.spinMySpin.adapter = adapter
    }
}