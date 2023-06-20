package com.example.submission1.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "FavoriteUser")
data class FavoriteUser(
    val login: String,
    @PrimaryKey
    val id : Int,
    val avatar_url : String,
    val type : String,
): Serializable
