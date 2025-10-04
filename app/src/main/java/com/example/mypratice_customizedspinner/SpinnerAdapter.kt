package com.example.mypratice_customizedspinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mypratice_customizedspinner.databinding.SpinnerItemBinding

class SpinnerAdapter(context: Context, data: ArrayList<SpinnerItemDataClass>):
    ArrayAdapter<SpinnerItemDataClass>(context, R.layout.spinner_item, data) {
    private lateinit var binding: SpinnerItemBinding
    fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view: View
            if(convertView == null){
                val binding = SpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
            }else{
                view = convertView
                binding = SpinnerItemBinding.bind(convertView)
            }
            val data = getItem(position)
            if(data != null){
                binding.textView.text = data.name
                binding.imageView.setImageResource(data.icon)
            }
            return binding.root
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return createView(position, convertView, parent)
    }
}