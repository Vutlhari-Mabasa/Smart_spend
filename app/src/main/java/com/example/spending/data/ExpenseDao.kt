package com.example.spending.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {

    // Inserts an expense into the database, replacing the existing one if the expense already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    // Retrieves a list of expenses that occurred between the given start and end date
    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getExpensesBetween(start: String, end: String): List<Expense>

    // Retrieves the total expenses for each category between the given start and end date.
    // It sums up the expenses per category and returns a list of CategoryTotal objects.
    @Query(
        "SELECT category, SUM(CAST(description AS DOUBLE)) AS total " +
                "FROM expenses WHERE date BETWEEN :startDate AND :endDate GROUP BY category"
    )
    suspend fun getTotalByCategoryBetween(startDate: String, endDate: String): List<CategoryTotal>
}
