package com.example.spending

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.spending.data.User
import com.example.spending.data.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activity that handles user registration
class RegisterActivity : AppCompatActivity() {

    // UI components for user input
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    // ViewModel for interacting with the user data (Room DB)
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Sets the layout for this screen

        // Initialize the UserViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Link the EditText and Button from the layout
        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        registerButton = findViewById(R.id.buttonRegister)

        // Handle click on the Register button
        registerButton.setOnClickListener {
            // Get the entered username and password, trimmed of whitespace
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Basic validation to check if fields are not empty
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Create a User object with the entered data
                val user = User(username = username, password = password)

                // Insert the user in the database on a background thread using Coroutine
                CoroutineScope(Dispatchers.IO).launch {
                    userViewModel.insertUser(user)

                    // After insertion, show success message and go to login screen on the main thread
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Registered Successfully", Toast.LENGTH_SHORT).show()

                        // Redirect to LoginActivity
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish() // Close RegisterActivity so it doesn't stay in the backstack
                    }
                }
            } else {
                // Show error if either field is empty
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
