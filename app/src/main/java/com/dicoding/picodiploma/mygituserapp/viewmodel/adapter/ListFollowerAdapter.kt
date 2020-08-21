package com.dicoding.picodiploma.mygituserapp.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.model.Follower
import kotlinx.android.synthetic.main.card_item.view.*

class ListFollowerAdapter(private val listFollower: ArrayList<Follower>):
    RecyclerView.Adapter<ListFollowerAdapter.ListViewHolder>(){

    companion object{
        const val IMG_WIDTH = 50
        const val IMG_HEIGHT = 50
    }

    fun setData(items: ArrayList<Follower>){
        listFollower.clear()
        listFollower.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_minimize, parent, false)
        return ListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return listFollower.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollower[position])
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Follower){
            with(itemView){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(
                        IMG_WIDTH,
                        IMG_HEIGHT
                    ))
                    .placeholder(R.drawable.baseline_account_circle_black_48)
                    .error(R.drawable.baseline_broken_image_black_48)
                    .into(img_profile)

                tv_fullname.text = user.fullname
                tv_username.text = itemView.context.getString(R.string.at, user.username)
            }
        }
    }
}