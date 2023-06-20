package com.example.submission1.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.data.User
import com.example.submission1.data.UserResponse
import com.example.submission1.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel(){
    val listUser = MutableLiveData<ArrayList<User>>()

    companion object {
        private const val TAG = "UserViewModel"
    }

    fun setSearchUsers(Query: String){
        RetrofitClient.apiInstance
            .getUsername(Query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                    else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                    t.printStackTrace()
                }

            })
    }

    fun getSearchUsers() : LiveData<ArrayList<User>>{
        return listUser
    }

}
































