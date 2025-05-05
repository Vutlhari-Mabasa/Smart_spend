package com.example.spending.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity class representing a single entry in the "monthly_goals" table in Room database
@Entity(tableName = "monthly_goals")
data class MonthlyGoal(
    // The PrimaryKey annotation designates this field as the unique identifier for the table entry.
    // We use a fixed id (1) for the goal because there is only one goal entry for the month.
    @PrimaryKey val id: Int = 1, // id = 1 is a constant because we are only storing one goal entry for the month

    // The minGoal column holds the minimum goal amount the user wants to spend in the month.
    @ColumnInfo(name = "min_goal") val minGoal: Double,

    // The maxGoal column holds the maximum goal amount the user wants to spend in the month.
    @ColumnInfo(name = "max_goal") val maxGoal: Double
)
