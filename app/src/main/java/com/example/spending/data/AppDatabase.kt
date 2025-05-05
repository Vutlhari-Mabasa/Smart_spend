package com.example.spending.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// This annotation defines the Room database, specifying its entities (tables) and version number.
// The database version is set to 1, and exportSchema is set to false, meaning Room will not export the schema
// when the database is compiled (this is mostly used in production and for database migrations).
@Database(
    entities = [User::class, Category::class, Expense::class, MonthlyGoal::class], // These are the entities (tables) in the database.
    version = 1,  // The version of the database. If any changes are made to the schema (e.g., new fields/tables),
    // the version number must be incremented.
    exportSchema = false  // Disables the export of the database schema.
)
abstract class AppDatabase : RoomDatabase() {

    // Abstract methods that provide access to DAOs (Data Access Objects).
    // DAOs provide methods for performing CRUD (Create, Read, Update, Delete) operations on the corresponding tables.

    abstract fun userDao(): UserDao  // Returns the DAO for the User table. Operations related to users (e.g., registration) are performed here.
    abstract fun categoryDao(): CategoryDao  // Returns the DAO for the Category table. Operations related to categories (e.g., adding or retrieving categories) are handled here.
    abstract fun expenseDao(): ExpenseDao  // Returns the DAO for the Expense table. Operations related to expenses (e.g., adding or viewing expenses) are done here.
    abstract fun monthlyGoalDao(): MonthlyGoalDao  // Returns the DAO for the MonthlyGoal table. Operations related to setting and checking monthly spending goals are handled here.

    // Companion object to ensure that the database instance is a singleton.
    companion object {
        // The INSTANCE variable holds a reference to the database instance. It is private and initialized as null.
        // This ensures that there is only one instance of the database in the application.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // This function returns the singleton database instance.
        // It ensures that only one instance of the database is created throughout the app.
        fun getDatabase(context: Context): AppDatabase {
            // If the INSTANCE is already initialized (not null), return it.
            return INSTANCE ?: synchronized(this) {
                // If INSTANCE is null, create the database instance.
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Application context is used to prevent memory leaks.
                    AppDatabase::class.java,     // The class of the database being created.
                    "expense_tracker_db"         // The name of the database file.
                ).build()  // The database is built using Room's database builder.

                // Save the instance for future use (so that the database instance is not recreated).
                INSTANCE = instance
                instance  // Return the database instance.
            }
        }
    }
}
