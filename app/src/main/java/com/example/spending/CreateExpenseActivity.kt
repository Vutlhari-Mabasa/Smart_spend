package com.example.spending

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.spending.data.AppDatabase
import com.example.spending.data.CategoryDao
import com.example.spending.data.Expense
import com.example.spending.data.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CreateExpenseActivity : AppCompatActivity() {

    // Database and DAO instances to interact with the Room database
    private lateinit var db: AppDatabase             // AppDatabase instance to interact with the Room database
    private lateinit var categoryDao: CategoryDao   // DAO for accessing the Category table
    private lateinit var expenseDao: ExpenseDao     // DAO for accessing the Expense table

    // UI components for displaying and accepting user input
    private lateinit var spinnerCategory: Spinner         // Dropdown spinner to select a category for the expense
    private lateinit var edtDescription: EditText         // EditText to enter the description of the expense
    private lateinit var edtAmount: EditText              // EditText to enter the amount of the expense
    private lateinit var imgPreview: ImageView            // ImageView to preview the selected image for the expense
    private lateinit var txtSelectedDate: TextView        // TextView to display the selected date for the expense
    private lateinit var txtStartTime: TextView           // TextView to display the selected start time of the expense
    private lateinit var txtEndTime: TextView             // TextView to display the selected end time of the expense

    // Data variables for storing the values entered by the user
    private var photoUri: Uri? = null         // URI for the photo selected by the user (could be an image file)
    private var selectedDate = ""            // Store the selected date in a string format (e.g., "01/05/2025")
    private var startTime = ""               // Store the selected start time (e.g., "09:00")
    private var endTime = ""                 // Store the selected end time (e.g., "17:00")

    // Registering an activity result launcher to pick an image from the device storage
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        photoUri = it               // The URI of the selected image is stored in photoUri
        imgPreview.setImageURI(photoUri)  // Set the selected image to the ImageView for preview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_expense)  // Set the layout for this activity

        // Initialize the database and DAO instances to interact with the Room database
        db = AppDatabase.getDatabase(this)  // Get the Room database instance
        categoryDao = db.categoryDao()     // Get the CategoryDao instance
        expenseDao = db.expenseDao()       // Get the ExpenseDao instance

        // Initialize UI components (widgets) that will be used to get input from the user
        edtDescription = findViewById(R.id.edtDescription)
        edtAmount = findViewById(R.id.edtAmount)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        imgPreview = findViewById(R.id.expenseImage)
        txtSelectedDate = findViewById(R.id.txtSelectedDate)
        txtStartTime = findViewById(R.id.txtStartTime)
        txtEndTime = findViewById(R.id.txtEndTime)

        // Set up the UI actions (button clicks) for selecting date, time, and image
        findViewById<Button>(R.id.btnSelectDate).setOnClickListener {
            pickDate()  // Open the DatePickerDialog for selecting a date
        }

        findViewById<Button>(R.id.btnStartTime).setOnClickListener {
            pickTime { time ->
                startTime = time             // Set the selected start time to the 'startTime' variable
                txtStartTime.text = "Start Time: $time"  // Display the start time in the TextView
            }
        }

        findViewById<Button>(R.id.btnEndTime).setOnClickListener {
            pickTime { time ->
                endTime = time               // Set the selected end time to the 'endTime' variable
                txtEndTime.text = "End Time: $time"  // Display the end time in the TextView
            }
        }

        findViewById<Button>(R.id.btnAddPhoto).setOnClickListener {
            pickImage.launch("image/*")  // Launch the image picker to select an image from the gallery
        }

        findViewById<Button>(R.id.btnSaveExpense).setOnClickListener {
            saveExpense()  // Save the expense data to the database
        }

        // Load categories from the database and populate the spinner (dropdown)
        loadCategories()
    }

    // Open a date picker dialog to allow the user to select a date
    private fun pickDate() {
        val calendar = Calendar.getInstance()  // Get the current date
        DatePickerDialog(this, { _, year, month, day ->
            selectedDate = "$day/${month + 1}/$year"  // Format the selected date as "dd/MM/yyyy"
            txtSelectedDate.text = "Selected Date: $selectedDate"  // Display the selected date in the TextView
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    // Open a time picker dialog to allow the user to select a time
    private fun pickTime(onTimePicked: (String) -> Unit) {
        val calendar = Calendar.getInstance()  // Get the current time
        TimePickerDialog(this, { _, hour, minute ->
            val time = String.format("%02d:%02d", hour, minute)  // Format the time as "HH:mm"
            onTimePicked(time)  // Pass the selected time to the callback function
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    // Load categories from the database and populate the spinner (dropdown) with the category names
    private fun loadCategories() {
        lifecycleScope.launch {
            val categories = categoryDao.getAllCategories()  // Get all categories from the database
            val adapter = ArrayAdapter(
                this@CreateExpenseActivity,
                android.R.layout.simple_spinner_item,
                categories.map { it.name }  // Extract category names from the Category entities
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            withContext(Dispatchers.Main) {
                spinnerCategory.adapter = adapter  // Set the adapter to the spinner
            }
        }
    }

    // Save the entered expense data to the database
    private fun saveExpense() {
        val description = edtDescription.text.toString().trim()  // Get the description entered by the user
        val category = spinnerCategory.selectedItem?.toString() ?: ""  // Get the selected category
        val amountText = edtAmount.text.toString().trim()  // Get the amount entered by the user
        val amount = amountText.toDoubleOrNull()  // Convert the amount to a Double, or null if invalid

        // Validate that all required fields are filled in correctly
        if (selectedDate.isEmpty() || startTime.isEmpty() || endTime.isEmpty() ||
            description.isEmpty() || category.isEmpty() || amount == null || amount <= 0
        ) {
            Toast.makeText(this, "Please fill all required fields with valid data", Toast.LENGTH_SHORT).show()
            return  // Exit if validation fails
        }

        val photoPath = photoUri?.toString() ?: ""  // Convert the photo URI to a string (empty if no photo selected)

        // Create an Expense object with the collected data
        val expense = Expense(
            date = selectedDate,
            startTime = startTime,
            endTime = endTime,
            description = description,
            category = category,
            amount = amount.toInt(),  // Convert the amount to Int for the database
            photoUri = photoPath      // Store the photo URI as a string
        )

        // Insert the expense into the database in a background thread
        lifecycleScope.launch {
            expenseDao.insert(expense)  // Insert the expense into the database
            withContext(Dispatchers.Main) {
                Toast.makeText(this@CreateExpenseActivity, "Expense saved", Toast.LENGTH_SHORT).show()
                finish()  // Close the activity and return to the previous screen
            }
        }
    }
}
