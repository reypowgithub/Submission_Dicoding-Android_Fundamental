package com.example.submission1.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission1.data.local.FavoriteUser
import com.example.submission1.data.local.FavoriteUserDao
import com.example.submission1.data.local.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao : FavoriteUserDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser() : LiveData<List<FavoriteUser>>?{
        return userDao?.getFavorite()
    }
}