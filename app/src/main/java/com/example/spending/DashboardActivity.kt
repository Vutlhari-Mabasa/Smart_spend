package com.example.spending

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// DashboardActivity is the main screen that acts as a navigation hub for other parts of the app
class DashboardActivity : AppCompatActivity() {

    // onCreate() is called when the activity is starting
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout resource for the dashboard screen
        setContentView(R.layout.activity_dashboard)

        // Set up click listeners for each button on the dashboard

        // Navigates to the screen where the user can add a new category
        findViewById<Button>(R.id.btnAddCategory).setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        // Navigates to the screen where the user can create a new expense
        findViewById<Button>(R.id.btnCreateExpense).setOnClickListener {
            startActivity(Intent(this, CreateExpenseActivity::class.java))
        }

        // Navigates to the screen where all expenses are listed
        findViewById<Button>(R.id.btnViewExpenses).setOnClickListener {
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        // Navigates to the screen where the user can set monthly spending goals
        findViewById<Button>(R.id.btnSetGoals).setOnClickListener {
            startActivity(Intent(this, MonthlyGoalActivity::class.java))
        }

        // Navigates to the screen where the user can view total expenses by category
        findViewById<Button>(R.id.btnViewTotalByCategory).setOnClickListener {
            startActivity(Intent(this, ViewTotalByCategoryActivity::class.java))
        }
    }
}
