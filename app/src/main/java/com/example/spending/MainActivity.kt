package com.example.spending

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

// MainActivity is the launcher screen that allows users to either log in or register.
class MainActivity : AppCompatActivity() {

    // SuppressLint is used here to avoid the IDE warning for MissingInflatedId
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sets the layout for this activity from XML (res/layout/activity_main.xml)
        setContentView(R.layout.activity_main)

        // Find the login and register buttons from the layout
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // When the user clicks the Login button, navigate to the LoginActivity
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // When the user clicks the Register button, navigate to the RegisterActivity
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
