package com.dicoding.picodiploma.mygituserapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.database.helper.UserHelper
import com.dicoding.picodiploma.mygituserapp.viewmodel.adapter.ListFavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        rv_fav_user.layoutManager = LinearLayoutManager(this)
        rv_fav_user.setHasFixedSize(true)
        val favoriteAdapter = ListFavoriteAdapter(this)
        rv_fav_user.adapter = favoriteAdapter

        val userHelper = UserHelper(this)
        userHelper.open()
        val userModels = userHelper.queryAll()
        userHelper.close()
    }
}