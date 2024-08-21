package com.example.cake.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cake.MAIN.Main_Window_Activity
import com.example.cake.Models.PopularModel
import com.example.cake.databinding.CardAddItemBinding


class CartAdapter(
    val context: Context,
    private var list: MutableList<PopularModel>,
    private val onItemUpdated: (List<PopularModel>) -> Unit // Добавлен коллбэк
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CardAddItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val listModel = list[position]

        holder.foodName.text = listModel.getFoodName()
        holder.foodPrice.text = listModel.getFoodPrice().toString()
        holder.foodCount.text = listModel.getFoodCount().toString()
        val myUrl = listModel.getFoodPicture() //"https://habrastorage.org/r/w1560/webt/rm/kt/z6/rmktz63wonvr2jf4eibvooq2aas.png"

        Glide.with(Main_Window_Activity.AppContext)
            .load(myUrl)
            .into(holder.foodImage)

        holder.plus.setOnClickListener {
            if (listModel.getFoodCount() < 5) {
                val count = listModel.getFoodCount() + 1
                listModel.setFoodCount(count)
                holder.foodCount.text = count.toString()
                onItemUpdated(list) // Уведомляем об обновлении
            }
        }

        holder.minus.setOnClickListener {
            if (listModel.getFoodCount() > 1) {
                val count = listModel.getFoodCount() - 1
                listModel.setFoodCount(count)
                holder.foodCount.text = count.toString()
                onItemUpdated(list) // Уведомляем об обновлении
            } else {
                removeItem(position)
            }
        }

        holder.delete.setOnClickListener {
            removeItem(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CartViewHolder(binding: CardAddItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val foodImage = binding.homeFoodImage
        val foodName = binding.homeFoodName
        val foodPrice = binding.homeFoodPrice
        val foodCount = binding.foodCount

        val plus = binding.plusBtn
        val minus = binding.minusBtn
        val delete = binding.deleteBtn
    }



    private fun removeItem(position: Int) {
        if (position in 0 until list.size) {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
            onItemUpdated(list) // Уведомляем об обновлении
        }
    }

}