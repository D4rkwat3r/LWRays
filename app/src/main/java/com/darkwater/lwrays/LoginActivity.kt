package com.darkwater.lwrays

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.darkwater.lwrays.network.APIClient
import com.darkwater.lwrays.utils.SessionHolder
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailInput = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordEditText)
        val submit = findViewById<Button>(R.id.loginSubmitButton)
        val client = APIClient(this)
        submit.setOnClickListener {
            client.login(emailInput.text.toString(), passwordInput.text.toString()) { response ->
                SessionHolder.setSession(response.sId)
                SessionHolder.setUid(response.account.uid)
                startActivity(Intent(this, MainScreenActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Вы не вошли в аккаунт!", Toast.LENGTH_SHORT).show()
    }
}