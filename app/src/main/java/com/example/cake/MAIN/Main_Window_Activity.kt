package com.example.cake.MAIN

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cake.Adapters.PopularAdapter
import com.example.cake.Models.PopularModel
import com.example.cake.R
import com.example.cake.databinding.ActivityMainWindowBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Main_Window_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainWindowBinding
    lateinit var listPopularItems : ArrayList<PopularModel>
    public lateinit var listNotifiers : ArrayList<PopularAdapter>

    public fun addNotifier(notifier: PopularAdapter){
        listNotifiers.add(notifier)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)



        listPopularItems = ArrayList()
        listNotifiers = ArrayList()

        Main_Window_Activity.AppContext = applicationContext
        // Получение и отображение сообщения об успешном заказе
        val message = intent.getStringExtra("message")
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        changeFragment(HomeFragment())

        loadDataset()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    changeFragment(HomeFragment())
                }

                R.id.cart ->{
                    changeFragment(CartFragment())
                }

                R.id.search ->{
                    changeFragment(SearcFragment())
                }


                R.id.profile ->{
                    changeFragment(ProfileFragment())
                }
            }
            return@setOnItemSelectedListener true

        }
    }
    companion object {
        lateinit var AppContext: Context
    }
    fun  changeFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    fun updateNotifiers() {
        for (notifier in listNotifiers) {
            notifier.updateList(listPopularItems)
        }
    }

    fun loadDataset() {
        lateinit var database: FirebaseDatabase
        database= FirebaseDatabase.getInstance("https://cakedeveliry-default-rtdb.europe-west1.firebasedatabase.app/")

        val tblProducts = database.reference.child("products")
        listPopularItems.clear()

        //tblProducts.orderByKey()[0]
        //database.reference.child("products").child("Cake1").child("Name").get().addOnSuccessListener {
        //    Log.d("TEST", "Test: ${it.value}")
        // }


        tblProducts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //listPopularItems = ArrayList()
                for (cakeItem in dataSnapshot.children) {
                    Log.d("TEST", "Name ${cakeItem.child("Name").value} Price: ${cakeItem.child("Price").value}")
                    listPopularItems.add(PopularModel( foodName = "${cakeItem.child("Name").value}".replace("_n", "\n"), (cakeItem.child("Price").value as Long).toInt(), "${cakeItem.child("Description").value}".replace("_n", "\n"),"${cakeItem.child("Ingredients").value}".replace("_n", "\n"), 1, "${cakeItem.child("Image").value}"))
                }
                updateNotifiers()

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}
