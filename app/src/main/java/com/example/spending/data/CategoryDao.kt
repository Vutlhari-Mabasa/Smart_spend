package com.example.spending.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// CategoryDao.kt
// Data Access Object (DAO) that defines methods for accessing and manipulating the 'categories' table in the Room database

@Dao
interface CategoryDao {

    // Insert a new category into the 'categories' table
    // onConflict = OnConflictStrategy.IGNORE ensures that if a category already exists, it is ignored (no overwrite)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)

    // Fetch all categories from the 'categories' table
    // This function returns a list of Category objects
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>
}
