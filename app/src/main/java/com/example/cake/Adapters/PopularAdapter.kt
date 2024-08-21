package com.example.cake.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cake.DetailsActivity
import com.example.cake.MAIN.Main_Window_Activity
import com.example.cake.Models.PopularModel
import com.example.cake.Models.SharedModel
import com.example.cake.databinding.HomeFoodItemBinding


class PopularAdapter(
    val contex : Context,
    var list : ArrayList<PopularModel>
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private lateinit var shareModel : SharedModel

    fun setShareModel(videoModel: SharedModel){
        shareModel = videoModel
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
       val binding = HomeFoodItemBinding.inflate(LayoutInflater.from(contex), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {

        val listModel = list[position]

          holder.foodName.text = listModel.getFoodName()
          holder.foodPrice.text = listModel.getFoodPrice().toString()

        val myUrl = listModel.getFoodPicture() //"https://habrastorage.org/r/w1560/webt/rm/kt/z6/rmktz63wonvr2jf4eibvooq2aas.png"

        Glide.with(Main_Window_Activity.AppContext)
                    .load(myUrl)
                    .into(holder.foodImage)

        holder.item.setOnClickListener {
            val intent = Intent(contex, DetailsActivity :: class.java)
            intent.putExtra("foodPicture", listModel.getFoodPicture())
            intent.putExtra("foodName", listModel.getFoodName())
            intent.putExtra("foodDescription", listModel.getFoodDescription())
            intent.putExtra("foodIngredient", listModel.getFoodIngredient())
            contex.startActivity(intent)
        }

        holder.addBtn.setOnClickListener{
            if (shareModel.inList(listModel)){
                shareModel.deleteFromCart(listModel)
                holder.addBtn.setText("В Корзину")

            }else{
                   shareModel.addToCart(listModel)
                   holder.addBtn.setText("Убрать")

            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PopularViewHolder(binding : HomeFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {


        val foodImage = binding.homeFoodImage
        val foodName = binding.homeFoodName
        val foodPrice = binding.homeFoodPrice




        val addBtn = binding.homeFoodBtn
        val item = binding.root



    }

    fun updateList(newList : ArrayList<PopularModel>){
        list = newList
        notifyDataSetChanged()
    }






}











