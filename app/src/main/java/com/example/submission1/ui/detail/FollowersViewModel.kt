package com.example.submission1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.api.RetrofitClient
import com.example.submission1.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()
    companion object {
        private const val TAG = "FollowersViewModel"
    }

    fun setListFollowers(username : String){
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowers.postValue(response.body())
                    }else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getListFollowers() : LiveData<ArrayList<User>>{
        return listFollowers

    }
}