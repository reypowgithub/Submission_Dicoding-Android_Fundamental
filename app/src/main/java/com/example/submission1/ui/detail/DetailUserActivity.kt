package com.example.submission1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submission1.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)!!
        val type = intent.getStringExtra(EXTRA_TYPE)!!


        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        showLoading(true)
        viewModel.getUserDetail().observe(this){
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowing.text = "${it.following}"
                    tvFollower.text = "${it.followers}"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                    tvLocation.text = it.location
                }
                showLoading(false)
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener{

            _isChecked =! _isChecked
            if (_isChecked){
                if (username != null) {
                    viewModel.addToFavorite(username, id, avatarUrl, type)
                }
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }


        val pagerAdapter = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = pagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}