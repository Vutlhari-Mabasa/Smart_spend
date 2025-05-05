package com.example.spending

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spending.data.AppDatabase
import com.example.spending.data.ExpenseDao
import com.example.spending.data.ExpensesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

// Activity to view expenses between two selected dates
class ViewExpensesActivity : AppCompatActivity() {

    // Declare database and DAO
    private lateinit var db: AppDatabase
    private lateinit var expenseDao: ExpenseDao

    // Adapter to bind expenses to RecyclerView
    private lateinit var expensesAdapter: ExpensesAdapter

    // Variables to store user-selected date range
    private var startDate = ""
    private var endDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        // Initialize database and DAO
        db = AppDatabase.getDatabase(this)
        expenseDao = db.expenseDao()

        // Get references to UI components
        val btnSelectStartDate = findViewById<Button>(R.id.btnSelectStartDate)
        val btnSelectEndDate = findViewById<Button>(R.id.btnSelectEndDate)
        val btnViewExpenses = findViewById<Button>(R.id.btnViewExpenses)
        val txtStartDate = findViewById<TextView>(R.id.txtStartDate)
        val txtEndDate = findViewById<TextView>(R.id.txtEndDate)
        val rvExpenses = findViewById<RecyclerView>(R.id.rvExpenses)

        // Setup RecyclerView with the adapter
        expensesAdapter = ExpensesAdapter()
        rvExpenses.layoutManager = LinearLayoutManager(this)
        rvExpenses.adapter = expensesAdapter

        // Show date picker for selecting start date
        btnSelectStartDate.setOnClickListener {
            pickDate { date ->
                startDate = date
                txtStartDate.text = "Start Date: $date"
            }
        }

        // Show date picker for selecting end date
        btnSelectEndDate.setOnClickListener {
            pickDate { date ->
                endDate = date
                txtEndDate.text = "End Date: $date"
            }
        }

        // Fetch and display expenses when user clicks "View Expenses"
        btnViewExpenses.setOnClickListener {
            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Please select both start and end dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Load expenses within the selected date range
            loadExpenses(startDate, endDate)
        }
    }

    // Function to open date picker and return selected date in DD/MM/YYYY format
    private fun pickDate(onDatePicked: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = "$day/${month + 1}/$year" // month+1 because it's 0-indexed
                onDatePicked(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Function to load expenses from Room DB between two dates and show in RecyclerView
    private fun loadExpenses(start: String, end: String) {
        lifecycleScope.launch {
            val expenses = expenseDao.getExpensesBetween(start, end) // Query DB
            withContext(Dispatchers.Main) {
                expensesAdapter.submitList(expenses) // Update RecyclerView
            }
        }
    }
}
