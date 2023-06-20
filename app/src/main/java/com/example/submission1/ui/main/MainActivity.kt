package com.example.submission1.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.R
import com.example.submission1.data.User
import com.example.submission1.databinding.ActivityMainBinding
import com.example.submission1.ui.detail.DetailUserActivity
import com.example.submission1.ui.favorite.FavoriteActivity
import com.example.submission1.ui.setting.ThemeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_TYPE, data.type)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserViewModel::class.java]

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            btnSearch.setOnClickListener{
                searchUsername()
            }

            etQuery.setOnKeyListener{ _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUsername()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this) {
            if (it.isEmpty()) {
                val errorMessage = "Username not Found"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                showLoading(false)
            } else {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun searchUsername() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter a username", Toast.LENGTH_SHORT).show()
                return
            }
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.switch_theme -> {
                Intent(this, ThemeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}





















