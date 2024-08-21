package com.example.cake.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.cake.MAIN.Main_Window_Activity
import com.example.cake.R
import com.google.firebase.auth.FirebaseAuth

class LoginUserActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private  val emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_user)
        auth = FirebaseAuth.getInstance()
        val signInEmail: EditText = findViewById(R.id.sign_in_email)
        val signInPassword: EditText = findViewById(R.id.sign_in_password)
        val signInBtn: AppCompatButton = findViewById(R.id.login_user)
        val signUpText: TextView = findViewById(R.id.go_sign_up_user)


        signUpText.setOnClickListener {
            val intent = Intent(this,SignUpUserActivity :: class.java)
            startActivity(intent)
        }


        signInBtn.setOnClickListener {
            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            if (email.isEmpty()||password.isEmpty()){
                if (email.isEmpty()){
                    signInEmail.error = "Введите email"
                }
                if (password.isEmpty()){
                    signInPassword.error = "Введите пароль"
                }
                Toast.makeText(this,"Ведите данные своего Аккаунта",Toast.LENGTH_LONG).show()
            }

            else if (!email.matches(emailPattern.toRegex())){
                signInEmail.error = "Введите действителный адрес электронной почты"
                Toast.makeText(this,"Некоректный email",Toast.LENGTH_LONG).show()
            }

            else if(password.length<6){
                signInPassword.error = "Пароль должен быть больше 6 символов"
                Toast.makeText(this,"Некоректный пароль",Toast.LENGTH_LONG).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this, Main_Window_Activity :: class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Что-то пошло не так",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}