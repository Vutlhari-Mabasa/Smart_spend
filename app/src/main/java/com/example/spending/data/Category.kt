package com.example.spending.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// This class represents the 'categories' table in the Room database
@Entity(tableName = "categories") // The 'categories' table in the database
data class Category(
    // 'id' is the primary key of the 'categories' table and will be auto-generated
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // 'name' represents the name of the category (e.g., "Food", "Transportation")
    @ColumnInfo(name = "name") val name: String
)
