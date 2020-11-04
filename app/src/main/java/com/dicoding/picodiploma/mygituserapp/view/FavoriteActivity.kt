package com.dicoding.picodiploma.mygituserapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.database.helper.MappingHelper
import com.dicoding.picodiploma.mygituserapp.database.helper.UserHelper
import com.dicoding.picodiploma.mygituserapp.model.Favorite
import com.dicoding.picodiploma.mygituserapp.viewmodel.adapter.ListFavoriteAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: ListFavoriteAdapter
    private lateinit var helper: UserHelper

    companion object {
        private const val EXTRA_FAVORITE = "extra_favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = getString(R.string.favorite)

        rv_fav_user.layoutManager = LinearLayoutManager(this)
        rv_fav_user.setHasFixedSize(true)
        adapter = ListFavoriteAdapter(this)
        rv_fav_user.adapter = adapter

        helper = UserHelper.getInstance(applicationContext)
        helper.open()

        if (savedInstanceState == null){
            loadFavoriteAsync()
        }else{
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_FAVORITE)
            if (list != null){
                adapter.listFavorite = list
            }
        }
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main){
            pb_loading.visibility = View.VISIBLE
            val defferedFavorites = async(Dispatchers.IO){
                val cursor = helper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            pb_loading.visibility = View.INVISIBLE
            val favorites = defferedFavorites.await()
            if (favorites.size > 0){
                adapter.listFavorite = favorites
            }else{
                adapter.listFavorite = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_fav_user, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_FAVORITE, adapter.listFavorite)
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteAsync()
    }
}