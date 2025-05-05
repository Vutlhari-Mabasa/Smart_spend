package com.example.spending.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses") // This annotation marks the class as an entity and specifies the table name in the database
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primary key for the expense entry, auto-generated
    @ColumnInfo(name = "description") val description: String, // The description of the expense (e.g., "Lunch at cafe")
    @ColumnInfo(name = "category") val category: String, // The category of the expense (e.g., "Food", "Transport")
    @ColumnInfo(name = "date") val date: String, // Date of the expense in "DD/MM/YYYY" format
    @ColumnInfo(name = "start_time") val startTime: String, // Start time of the expense (e.g., "12:30")
    @ColumnInfo(name = "end_time") val endTime: String, // End time of the expense (e.g., "13:00")
    @ColumnInfo(name = "amount") val amount: Int, // The amount spent, stored as an integer (e.g., 500 for $5.00)
    @ColumnInfo(name = "photo_uri") val photoUri: String? // Optional URI for a photo associated with the expense (nullable)
)
