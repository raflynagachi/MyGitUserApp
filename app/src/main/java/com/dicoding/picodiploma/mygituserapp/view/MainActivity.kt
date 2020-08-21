package com.dicoding.picodiploma.mygituserapp.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.model.User
import com.dicoding.picodiploma.mygituserapp.viewmodel.adapter.ListUserAdapter
import com.dicoding.picodiploma.mygituserapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listDataUser: ArrayList<User> = ArrayList()
    private lateinit var listAdapter: ListUserAdapter
    private lateinit var mainViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter =
            ListUserAdapter(
                listDataUser
            )
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        viewConfig()
        runGetDataGit()
        configMainViewModel(listAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                if (query!!.isNotEmpty()) {
                    listDataUser.clear()
                    viewConfig()
                    mainViewModel.getDataUserSearch(query, applicationContext)
                    showLoading(true)
                    configMainViewModel(listAdapter)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_loading.visibility = View.VISIBLE
        } else {
            pb_loading.visibility = View.INVISIBLE
        }
    }

    private fun viewConfig() {
        rv_gituser.layoutManager = LinearLayoutManager(this)
        rv_gituser.setHasFixedSize(true)

        listAdapter.notifyDataSetChanged()
        rv_gituser.adapter = listAdapter
    }

    private fun runGetDataGit() {
        mainViewModel.getDataUser(applicationContext)
        showLoading(true)
    }


    private fun configMainViewModel(adapter: ListUserAdapter) {
        mainViewModel.getListUser().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

}