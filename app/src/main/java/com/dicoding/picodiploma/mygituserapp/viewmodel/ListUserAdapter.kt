package com.dicoding.picodiploma.mygituserapp.viewmodel

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.model.User
import com.dicoding.picodiploma.mygituserapp.view.DetailActivity
import kotlinx.android.synthetic.main.card_item.view.*

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    companion object{
        const val IMG_WIDTH = 100
        const val IMG_HEIGHT = 100
    }

    fun setData(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
        //setIntent here
        val data = listUser[position]
        holder.itemView.setOnClickListener {
            val dataUserIntent = User(
                data.fullname,
                data.username,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.follower,
                data.following
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, dataUserIntent)
            it.context.startActivity(mIntent)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User){
            with(itemView){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(IMG_WIDTH, IMG_HEIGHT))
                    .placeholder(R.drawable.baseline_account_circle_black_24)
                    .error(R.drawable.baseline_broken_image_black_24)
                    .into(img_profile)

                tv_fullname.text = user.fullname
                tv_username.text = itemView.context.getString(R.string.at, user.username)
                tv_repository.text = itemView.context.getString(R.string.repository, user.repository)
                tv_follower.text = itemView.context.getString(R.string.follower, user.follower)
                tv_following.text = itemView.context.getString(R.string.following, user.following)
            }
        }
    }
}