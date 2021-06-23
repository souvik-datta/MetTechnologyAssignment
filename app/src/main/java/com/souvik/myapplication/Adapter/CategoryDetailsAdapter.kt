package com.souvik.myapplication.Adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.souvik.myapplication.Model.Data
import com.souvik.myapplication.Model.Item
import com.souvik.myapplication.R
import com.souvik.myapplication.databinding.CategoryRowBinding

class CategoryDetailsAdapter(
    val list: ArrayList<Data>,
    val onCategoryClickListner: CategoryDetailsAdapter.OnItemClickListener
) : RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryDetailsAdapter.ViewHolder {
        val binding: CategoryRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.category_row, parent, false
        )
        return ViewHolder(binding,onCategoryClickListner)
    }

    override fun onBindViewHolder(holder: CategoryDetailsAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: CategoryRowBinding,val onCategoryClickListner: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data){
            binding.tvCategoryName.text = data.cat_name
            if(data.isSelected){
                binding.vwSelected.visibility = View.VISIBLE
            }else{
                binding.vwSelected.visibility = View.GONE
            }
            binding.clContainer.setOnClickListener {
                binding.vwSelected.visibility = View.VISIBLE
                onCategoryClickListner.onCategorySelected(data.items,adapterPosition)
                data.isSelected = true
            }
        }
    }

    interface OnItemClickListener {
        fun onCategorySelected(productList: List<Item>,position: Int)
    }
}