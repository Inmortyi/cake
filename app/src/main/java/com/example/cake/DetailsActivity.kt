package com.example.cake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cake.databinding.ActivityDeteilsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDeteilsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeteilsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodPicture = intent.getStringExtra("foodPicture")
        val foodName = intent.getStringExtra("foodName")
        val foodDescription = intent.getStringExtra("foodDescription")
        val foodIngredient = intent.getStringExtra("foodIngredient")

        foodPicture?.let {
            Glide.with(this)
                .load(it)
                .into(binding.menuDFoodImage)
        }

        binding.menuDFoodName.text = foodName
        binding.menuDFoodIngredient.text = foodIngredient
        binding.shortDescriptionOfFood.text = foodDescription

        binding.backHome.setOnClickListener{
            finish()
        }
    }
}