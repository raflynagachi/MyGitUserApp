package com.dicoding.picodiploma.mygituserapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.model.Following
import com.dicoding.picodiploma.mygituserapp.model.User
import com.dicoding.picodiploma.mygituserapp.viewmodel.FollowingViewModel
import com.dicoding.picodiploma.mygituserapp.viewmodel.ListFollowingAdapter
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowingFragment : Fragment() {

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<Following> = ArrayList()
    private lateinit var adapter: ListFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListFollowingAdapter(listData)
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra(EXTRA_DETAIL) as User
        configRv()

        followingViewModel.getDataUser(activity!!.applicationContext, dataUser.username.toString())
        showLoading(true)

        followingViewModel.getListUser().observe(activity!!, Observer { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun configRv() {
        rv_follower.layoutManager = LinearLayoutManager(activity)
        rv_follower.setHasFixedSize(true)
        rv_follower.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_follower.visibility = View.VISIBLE
        } else {
            pb_follower.visibility = View.INVISIBLE
        }
    }
}