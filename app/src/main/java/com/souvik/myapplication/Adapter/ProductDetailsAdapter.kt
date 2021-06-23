package com.souvik.myapplication.Adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.souvik.myapplication.Model.Item
import com.souvik.myapplication.R
import com.souvik.myapplication.databinding.ItemRowBinding

class ProductDetailsAdapter(val list: ArrayList<Item>,
                            val onItemRowClickListner: OnItemRowClickListner)
    : RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailsAdapter.ViewHolder {
        val binding: ItemRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_row, parent, false
        )
        return ViewHolder(binding,onItemRowClickListner)
    }

    override fun onBindViewHolder(holder: ProductDetailsAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemRowBinding,val onItemRowClickListner: OnItemRowClickListner) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Item) {
            binding.tvProductName.text = data.product_name
            binding.tvProductCategory.text = data.category_name
            binding.tvPrice.text = "\u0024 ${String.format("%.2f",data.price)}"
            binding.tvValue.text = data.quantity.toString()
            Glide.with(binding.ivImage.context)
                .load(data.product_image)
                .error(R.drawable.ic_error_image)
                .into(binding.ivImage)
            binding.ivAddFav.setOnClickListener {
                onItemRowClickListner.onFavClick(adapterPosition)
            }
            binding.clContainer.setOnClickListener {
                onItemRowClickListner.onContainerClick(adapterPosition)
            }
            binding.ibIncrement.setOnClickListener {
                var value = binding.tvValue.text.toString().toInt()
                binding.tvValue.text = (++value).toString()
                data.quantity = value
            }
            binding.ibDecrement.setOnClickListener {
                var value = binding.tvValue.text.toString().toInt()
                if(value-1>-1) {
                    binding.tvValue.text = (--value).toString()
                    data.quantity = value
                }
            }
        }
    }

    interface OnItemRowClickListner {
        fun onFavClick(position: Int)
        fun onContainerClick(position: Int)
    }
}