package com.example.cake.MAIN

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cake.databinding.ActivityPayBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class PayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получение данных из Intent

        val totalPrice = intent.getIntExtra("totalPrice", 0)

        val orderItems = intent.getStringExtra("orderItems")

        // Установка значений в TextView
        binding.totalPrice.text = totalPrice.toString()
        binding.orderItems.text = orderItems
        // Логика для обработки заказа
        binding.proceedOrderBtn.setOnClickListener {
          //  sendOrderEmail(orderItems, totalPrice)
            saveOrderToFirebase(orderItems, totalPrice)
        }
    }

    private fun saveOrderToFirebase(orderItems: String?, totalPrice: Int) {
        // Установка URL базы данных
        val database =
            FirebaseDatabase.getInstance("https://cakedeveliry-default-rtdb.europe-west1.firebasedatabase.app/")
        val ordersRef = database.getReference("orders")
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userId = user.uid
            val orderId = ordersRef.push().key
            if (orderId != null) {
                val order = mapOf(
                    "orderId" to orderId,
                    "orderItems" to orderItems,
                    "totalPrice" to totalPrice,
                    "userId" to userId,
                )

                ordersRef.child(orderId).setValue(order)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("PayActivity", "Order saved successfully")
                            // Переход на MainWindowActivity с сообщением об успехе
                            val intent = Intent(this, Main_Window_Activity::class.java).apply {
                                putExtra("message", "Заказ успешно оформлен!")
                            }
                            startActivity(intent)
                        } else {
                            Log.e("PayActivity", "Не удалось сохранить заказ", task.exception)
                        }
                    }
            } else {
                Log.e("PayActivity", "Не удалось сгенерировать ID заказа")
            }
        } else {
            Log.e("PayActivity", "Пользователь не авторизован")
        }
    }

   /* private fun sendOrderEmail(orderItems: String?, totalPrice: Int) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userEmail = user.email
            val subject = "Новый заказ"
            val message =
                "Список заказанных товаров:\n${orderItems ?: ""}\n\nОбщая сумма заказа: $totalPrice"

            // Создание Intent для отправки email
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // только email приложения
                putExtra(Intent.EXTRA_EMAIL, arrayOf("limefej391@lisoren.com")) // Адрес получателя
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.e("PayActivity", "Не найдено приложение для отправки email")
                // Здесь можно добавить логику для случаев, когда нет подходящего приложения для отправки email
            }
        } else {
            Log.e("PayActivity", "Пользователь не авторизован")
            // Здесь можно добавить логику для случаев, когда пользователь не авторизован
        }
    }*/
}