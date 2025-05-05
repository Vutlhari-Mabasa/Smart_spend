package com.example.spending.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// UserViewModel.kt
class UserViewModel(application: Application) : AndroidViewModel(application) {

    // userDao: DAO object for accessing User data from the Room database
    // It interacts with the User table in the database.
    private val userDao = AppDatabase.getDatabase(application).userDao()

    // insertUser: Function to insert a new user into the database.
    // This function is called by the UI (usually through a button click).
    // It calls the DAO's insertUser function in a coroutine to avoid blocking the main thread.
    fun insertUser(user: User) = viewModelScope.launch {
        userDao.insertUser(user)
    }

    // authenticate: Function to authenticate a user based on the provided username and password.
    // It calls the authenticate function from the userDao, which queries the database for a matching username and password.
    // This is done within a suspend function since database queries are asynchronous.
    suspend fun authenticate(username: String, password: String): User? {
        return userDao.authenticate(username, password)
    }
}
