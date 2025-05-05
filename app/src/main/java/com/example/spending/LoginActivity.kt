package com.example.spending

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.spending.data.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activity responsible for logging in the user
class LoginActivity : AppCompatActivity() {

    // Declare UI components that will be used to capture user input
    private lateinit var usernameEditText: EditText  // EditText for username input
    private lateinit var passwordEditText: EditText  // EditText for password input
    private lateinit var loginButton: Button         // Button for submitting login credentials

    // ViewModel for handling user authentication logic
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)  // Set the layout for the activity (UI)

        // Initialize UI components by their IDs from the layout
        usernameEditText = findViewById(R.id.editTextUsernameLogin)  // Retrieve the EditText for username
        passwordEditText = findViewById(R.id.editTextPasswordLogin)  // Retrieve the EditText for password
        loginButton = findViewById(R.id.buttonLogin)  // Retrieve the Login button

        // Create an instance of UserViewModel to handle authentication logic
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Set an OnClickListener for the login button to handle login submission
        loginButton.setOnClickListener {
            // Get the text entered in the username and password fields, trimming any extra spaces
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Check if either username or password field is empty, and show a toast if so
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // Exit the method if validation fails
            }

            // Launch a coroutine to perform the authentication in a background thread (using IO dispatcher)
            CoroutineScope(Dispatchers.IO).launch {
                // Call the authenticate method of UserViewModel to check credentials
                val user = userViewModel.authenticate(username, password)

                // Switch to the main thread to update the UI (because UI updates must happen on the main thread)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        // If authentication is successful (user is not null), show success message
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                        // Navigate to the DashboardActivity (main screen of the app)
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))

                        // Close the login activity so the user cannot go back to it by pressing the back button
                        finish()
                    } else {
                        // If authentication fails (user is null), show an error message
                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
