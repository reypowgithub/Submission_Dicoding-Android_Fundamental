package com.example.submission1.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission1.api.RetrofitClient
import com.example.submission1.data.DetailUserResponse
import com.example.submission1.data.local.FavoriteUser
import com.example.submission1.data.local.FavoriteUserDao
import com.example.submission1.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao : FavoriteUserDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }
    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id : Int, avatar_url : String, type : String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatar_url,
                type

            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id : Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}

















