package com.example.spending

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spending.data.AppDatabase
import com.example.spending.data.CategoryTotal
import com.example.spending.data.CategoryTotalAdapter
import com.example.spending.data.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

// This activity displays total expenses grouped by category between selected dates
class ViewTotalByCategoryActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase               // Reference to Room database
    private lateinit var expenseDao: ExpenseDao        // DAO for accessing expense data
    private lateinit var categoryTotalAdapter: CategoryTotalAdapter // Adapter for RecyclerView

    private var startDate = ""                         // User-selected start date
    private var endDate = ""                           // User-selected end date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_total_by_category)

        // Initialize database and DAO
        db = AppDatabase.getDatabase(this)
        expenseDao = db.expenseDao()

        // Reference UI components
        val btnSelectStartDate = findViewById<Button>(R.id.btnSelectStartDate)
        val btnSelectEndDate = findViewById<Button>(R.id.btnSelectEndDate)
        val btnViewTotalByCategory = findViewById<Button>(R.id.btnViewTotalByCategory)

        // Setup RecyclerView for showing category totals
        val rvCategoryTotal = findViewById<RecyclerView>(R.id.rvCategoryTotal)
        categoryTotalAdapter = CategoryTotalAdapter()
        rvCategoryTotal.layoutManager = LinearLayoutManager(this)
        rvCategoryTotal.adapter = categoryTotalAdapter

        // Date picker for start date
        btnSelectStartDate.setOnClickListener {
            pickDate { date ->
                startDate = date
            }
        }

        // Date picker for end date
        btnSelectEndDate.setOnClickListener {
            pickDate { date ->
                endDate = date
            }
        }

        // Load totals after both dates are selected
        btnViewTotalByCategory.setOnClickListener {
            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Please select both start and end dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loadCategoryTotals(startDate, endDate)
        }
    }

    // Opens a calendar dialog and returns selected date as a string (e.g., "24/4/2025")
    private fun pickDate(onDatePicked: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = "$day/${month + 1}/$year"
                onDatePicked(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Loads total expenses per category for a given date range using a coroutine
    private fun loadCategoryTotals(start: String, end: String) {
        lifecycleScope.launch {
            // Call DAO method to get grouped data between selected dates
            val categoryTotals = expenseDao.getTotalByCategoryBetween(start, end)
            withContext(Dispatchers.Main) {
                // Submit list to RecyclerView adapter to display on UI
                categoryTotalAdapter.submitList(categoryTotals as List<CategoryTotal>)
            }
        }
    }
}
