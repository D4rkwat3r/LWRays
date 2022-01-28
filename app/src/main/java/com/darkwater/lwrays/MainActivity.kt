package com.darkwater.lwrays

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.darkwater.lwrays.network.APIClient
import com.darkwater.lwrays.utils.SessionHolder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (SessionHolder.extractSession() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}