package com.example.submission1.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getFavorite() : LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM FavoriteUser WHERE FavoriteUser.id = :id")
    suspend fun checkUser(id : Int) : Int

    @Query("DELETE FROM FavoriteUser WHERE FavoriteUser.id = :id")
    suspend fun removeFromFavorite(id : Int): Int
}