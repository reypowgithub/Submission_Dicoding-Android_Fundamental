package com.example.submission1.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.data.User
import com.example.submission1.data.local.FavoriteUser
import com.example.submission1.databinding.ActivityFavoriteBinding
import com.example.submission1.ui.detail.DetailUserActivity
import com.example.submission1.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel : FavoriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_TYPE, data.type)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url,
                user.type
            )
            listUser.add(userMapped)

        }
        return listUser
    }
}




















