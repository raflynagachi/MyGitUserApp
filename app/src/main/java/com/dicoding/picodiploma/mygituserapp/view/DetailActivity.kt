package com.dicoding.picodiploma.mygituserapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.model.User
import com.dicoding.picodiploma.mygituserapp.viewmodel.ViewPagerDetailAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (supportActionBar != null) {
            supportActionBar?.title = "Detail User"
        }

        setData()
        viewPagerConfig()
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra(EXTRA_DETAIL) as User
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions().override(150, 130))
            .into(img_detail_profile)
        tv_detail_fullname.text = dataUser.fullname
        tv_detail_username.text = getString(R.string.at, dataUser.username)
        tv_detail_company.text = dataUser.company
        tv_detail_location.text = dataUser.location
        tv_detail_following.text = getString(R.string.following, dataUser.following)
        tv_detail_follower.text = getString(R.string.follower, dataUser.follower)
        tv_detail_repository.text = getString(R.string.repository, dataUser.repository)
    }

    private fun viewPagerConfig() {
        val viewPagerDetail = ViewPagerDetailAdapter(this, supportFragmentManager)
        viewpager.adapter = viewPagerDetail
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }
}