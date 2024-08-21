package com.example.cake.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cake.MAIN.Main_Window_Activity
import com.example.cake.R
import com.example.cake.databinding.ActivitySignUpUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpUserActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var database: FirebaseDatabase
    private  val emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var binding: ActivitySignUpUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user)
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance("https://cakedeveliry-default-rtdb.europe-west1.firebasedatabase.app/")

        val signUpName :EditText = findViewById(R.id.sign_up_user_name)
        val signUpEmail: EditText =findViewById(R.id.sign_up_user_email)
        val signUpPhone: EditText =findViewById(R.id.sign_up_user_phone)
        val signUpPassword: EditText =findViewById(R.id.sign_in_user_password)
        val signUpCPassword: EditText =findViewById(R.id.sign_in_user_con_password)

        val signUpBtn: Button = findViewById(R.id.create_account_btn)


        signUpBtn.setOnClickListener {
            val name = signUpName.text.toString()
            val email = signUpEmail.text.toString()
            val phone = signUpPhone.text.toString()
            val password = signUpPassword.text.toString()
            val cpassword = signUpCPassword.text.toString()
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
                if (name.isEmpty()) {
                    signUpName.error = "Введите свое имя"
                }
                if (email.isEmpty()) {
                    signUpEmail.error = "Введите корректно email адрес"
                }
                if (email.isEmpty()) {
                    signUpPhone.error = "Введите корректно номер телефона"
                }
                if (password.isEmpty()) {
                    signUpPassword.error = "Пароль не должен быть пустым"
                }
                if (cpassword.isEmpty()) {
                    signUpCPassword.error = "Пароли не совпадают"
                }
                Toast.makeText(this,"Что то пошло не так",Toast.LENGTH_LONG).show()
            }
            else if (!email.matches(emailPattern.toRegex())){
                signUpEmail.error="Некорректный email адрес"
            }

            else if(password.length<6){
                signUpPassword.error="Пароль должен быть больше 6 символов"
                Toast.makeText(this,"Пароль должен быть больше 6 символов",Toast.LENGTH_LONG).show()
            }
            else if(password!=cpassword){
                signUpCPassword.error="Пароль не совпадает"
                Toast.makeText(this,"Пароль не совпадает",Toast.LENGTH_LONG).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val database =
                            database.reference.child("users").child(auth.currentUser!!.uid)
                        val users: Users = Users(name,email,phone,password,auth.currentUser!!.uid)
                        database.setValue(users).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, Main_Window_Activity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Что то пошло не так ,попробуйте снова",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}