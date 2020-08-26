package com.dicoding.picodiploma.mygituserapp.view

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWER
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.FULLNAME
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.dicoding.picodiploma.mygituserapp.database.helper.UserHelper
import com.dicoding.picodiploma.mygituserapp.model.User
import com.dicoding.picodiploma.mygituserapp.viewmodel.adapter.ViewPagerDetailAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
        const val IMG_HEIGHT = 130
        const val IMG_WIDTH = 150
    }

    private lateinit var helper: UserHelper
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (supportActionBar != null) {
            supportActionBar?.title = "Detail User"
        }

        helper = UserHelper.getInstance(applicationContext)
        helper.open()

        val usernameDetail = intent.getParcelableExtra(EXTRA_DETAIL) as User
        val cursor: Cursor = helper.queryByUsername(usernameDetail.username.toString())
        if (cursor.moveToNext()){
            statusFavorite = true
            setStatusFavorite(true)
        }

        fab_fav.setOnClickListener(this)

        setData()
        viewPagerConfig()
    }

    private fun setStatusFavorite(status: Boolean) {
        if (status){
            fab_fav.setImageResource(R.drawable.baseline_favorite_white_24)
        }else{
            fab_fav.setImageResource(R.drawable.baseline_favorite_border_white_24)
        }
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra(EXTRA_DETAIL) as User
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions().override(IMG_WIDTH, IMG_HEIGHT))
            .placeholder(R.drawable.baseline_account_circle_black_48)
            .error(R.drawable.baseline_broken_image_black_48)
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
        val viewPagerDetail =
            ViewPagerDetailAdapter(
                this,
                supportFragmentManager
            )
        viewpager.adapter = viewPagerDetail
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }

    override fun onClick(v: View?) {
        val data = intent.getParcelableExtra(EXTRA_DETAIL) as User
        when(v?.id){
            R.id.fab_fav ->{
                if (statusFavorite){
                    val username = data.username.toString()
                    helper.deleteByUsername(username)
                    Toast.makeText(this, "Data dihapus dari Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(false)
                    statusFavorite = false
                }else{
                    val values = ContentValues()
                    values.put(FULLNAME, data.fullname)
                    values.put(USERNAME, data.username)
                    values.put(AVATAR_URL, data.avatar)
                    values.put(COMPANY, data.company)
                    values.put(LOCATION, data.location)
                    values.put(REPOSITORY, data.repository)
                    values.put(FOLLOWER, data.follower)
                    values.put(FOLLOWING, data.following)
                    helper.insert(values)

                    statusFavorite = true
                    Toast.makeText(this, "Data ditambahkan ke Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(true)
                }
            }
        }
    }
}