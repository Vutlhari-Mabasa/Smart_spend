package com.example.spending

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.spending.data.AppDatabase
import com.example.spending.data.MonthlyGoal
import com.example.spending.data.MonthlyGoalDao
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activity for setting and saving monthly spending goals
class MonthlyGoalActivity : AppCompatActivity() {

    // Declare database and DAO variables
    private lateinit var db: AppDatabase
    private lateinit var monthlyGoalDao: MonthlyGoalDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_goal)

        // Initialize Room database and DAO
        db = AppDatabase.getDatabase(this)
        monthlyGoalDao = db.monthlyGoalDao()

        // Get references to UI elements
        val edtMinGoal = findViewById<TextInputEditText>(R.id.edtMinGoal)
        val edtMaxGoal = findViewById<TextInputEditText>(R.id.edtMaxGoal)
        val btnSaveGoals = findViewById<Button>(R.id.btnSaveGoals)

        // Load existing saved goals when the activity starts
        loadGoals()

        // Set click listener to save button
        btnSaveGoals.setOnClickListener {
            val minGoal = edtMinGoal.text.toString().trim()
            val maxGoal = edtMaxGoal.text.toString().trim()

            // Validate that both goal fields are not empty
            if (minGoal.isEmpty() || maxGoal.isEmpty()) {
                Toast.makeText(this, "Please enter both goals", Toast.LENGTH_SHORT).show()
            } else {
                // Save the goals into the database using a coroutine
                lifecycleScope.launch {
                    val goal = MonthlyGoal(
                        minGoal = minGoal.toDouble(),  // Convert input to Double
                        maxGoal = maxGoal.toDouble()
                    )

                    // Insert the goal into the database
                    monthlyGoalDao.insert(goal)

                    // Show confirmation message on UI thread
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MonthlyGoalActivity,
                            "Goals saved!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    // Function to load existing goal values from the database
    private fun loadGoals() {
        lifecycleScope.launch {
            val goal = monthlyGoalDao.getGoal()  // Fetch current goal (if any)
            withContext(Dispatchers.Main) {
                if (goal != null) {
                    // If a goal exists, populate the input fields with the current values
                    findViewById<TextInputEditText>(R.id.edtMinGoal).setText(goal.minGoal.toString())
                    findViewById<TextInputEditText>(R.id.edtMaxGoal).setText(goal.maxGoal.toString())
                }
            }
        }
    }
}
