package com.example.spending

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.spending.data.AppDatabase
import com.example.spending.data.Category
import com.example.spending.data.CategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activity for adding a new expense category
class AddCategoryActivity : AppCompatActivity() {

    // Declare database and DAO references
    private lateinit var db: AppDatabase
    private lateinit var categoryDao: CategoryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        // Initialize the Room database and DAO
        db = AppDatabase.getDatabase(this)
        categoryDao = db.categoryDao()

        // Get references to UI components
        val edtCategoryName = findViewById<EditText>(R.id.edtCategoryName)
        val btnSaveCategory = findViewById<Button>(R.id.btnSaveCategory)

        // Set click listener on "Save Category" button
        btnSaveCategory.setOnClickListener {
            val categoryName = edtCategoryName.text.toString().trim()

            // Validate that the category name is not empty
            if (categoryName.isEmpty()) {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            } else {
                // Save the category in a coroutine to avoid blocking the UI thread
                lifecycleScope.launch {
                    // Insert the new category into the database
                    categoryDao.insert(Category(name = categoryName))

                    // Show a success message and close the activity
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddCategoryActivity,
                            "Category '$categoryName' saved!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // Close the activity and return to previous screen
                    }
                }
            }
        }
    }
}
