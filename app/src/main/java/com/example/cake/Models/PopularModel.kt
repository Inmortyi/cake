package com.example.cake.Models

import android.util.Log

class PopularModel {

    private var foodImage : Int? = null
    private var foodName : String = ""
    private var foodPrice : Int = 0
    private var foodDescription : String = ""
    private var foodIngredient : String = ""
    private var foodCount : Int = 0
    private var foodId : String = ""
    private var foodPicture : String = ""


    constructor(foodImage: Int?, foodName: String, foodPrice: Int, foodDescription: String, foodIngredient: String, foodCount : Int, /* foodId : String */) {
        this.foodImage = foodImage
        this.foodName = foodName
        this.foodPrice = foodPrice
        this.foodDescription = foodDescription
        this.foodIngredient = foodIngredient
        this.foodCount = foodCount
     //   this.foodId = foodId

    }

    constructor(foodName: String, foodPrice: Int, foodDescription: String, foodIngredient: String, foodCount : Int, foodPicture: String) {

        this.foodName = foodName
        this.foodPrice = foodPrice
        this.foodDescription = foodDescription
        this.foodIngredient = foodIngredient
        this.foodCount = foodCount
        this.foodPicture = foodPicture

        Log.d("TEST", "Init value price: " + foodPrice.toString() + " " + "count: " + foodCount.toString())
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        this.foodId = (1..10)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun getFoodImage() : Int?{
        return foodImage
    }

    fun getFoodCount() : Int{
        return foodCount
    }

    fun getFoodName() : String{
        return  foodName
    }

    fun getFoodPrice() : Int{
        return foodPrice
    }

    fun getFoodDescription() : String{
        return foodDescription
    }

    fun getFoodIngredient() : String{
        return foodIngredient
    }

    fun getFoodID() : String{
        return foodId
    }

    fun getFoodPicture() : String{
        return foodPicture
    }

    fun setFoodImage(foodImage: Int?){
        this.foodImage = foodImage
    }

    fun setFoodCount(foodCount: Int){
        this.foodCount = foodCount
    }

    fun setFoodName(foodName: String){
        this.foodName = foodName
    }

    fun setFoodPrice(foodPrice: Int){
        this.foodPrice = foodPrice
    }

    fun  setFoodDescription(foodDescription: String){
        this.foodDescription = foodDescription
    }

    fun  setFoodIngredient(foodIngredient: String){
        this.foodIngredient = foodIngredient
    }

    fun setFoodId(foodId: String){
        this.foodId = foodId
    }

    fun setFoodPicture(foodPicture: String){
        this.foodPicture = foodPicture
    }

}