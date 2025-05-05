package com.example.spending.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object (DAO) for the User entity. This interface provides methods to interact with the User table in the Room database.
@Dao
interface UserDao {

    // This method inserts a user into the database.
    // The `onConflict = OnConflictStrategy.REPLACE` means that if there is already a user with the same ID or unique field (e.g., username),
    // the existing user will be replaced with the new user data.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // This query is used to authenticate a user by their username and password.
    // It looks for a user in the database where the username and password match the ones provided.
    // If no matching user is found, it returns `null`.
    // The `suspend` keyword indicates that this function should be called within a coroutine,
    // meaning the database query will run asynchronously to avoid blocking the main thread.
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    suspend fun authenticate(username: String, password: String): User?
}
