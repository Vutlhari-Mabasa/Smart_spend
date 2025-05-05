package com.example.spending.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// DAO (Data Access Object) for interacting with the 'monthly_goals' table in the database
@Dao
interface MonthlyGoalDao {

    // Insert a new MonthlyGoal into the database. If there's a conflict (duplicate ID), it will replace the existing entry.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(monthlyGoal: MonthlyGoal)

    // Query to retrieve the monthly goal, which is stored with a fixed ID (in this case, '1').
    // Returns a single MonthlyGoal object if it exists.
    @Query("SELECT * FROM monthly_goals WHERE id = 1")
    suspend fun getGoal(): MonthlyGoal?
}
