package com.example.spending.data

import androidx.room.ColumnInfo

// Data class that represents a total expense per category
data class CategoryTotal(

    // Column for the category name, i.e., the category of the expense (e.g., "Food", "Transport")
    @ColumnInfo(name = "category")
    val category: String,

    // Column for the total amount spent in the specific category
    @ColumnInfo(name = "total")
    val total: Double
)
