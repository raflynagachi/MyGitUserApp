package com.dicoding.picodiploma.mygituserapp.viewmodel.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.mygituserapp.CustomItemClickListener
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.model.Favorite
import com.dicoding.picodiploma.mygituserapp.model.User
import com.dicoding.picodiploma.mygituserapp.view.DetailActivity
import kotlinx.android.synthetic.main.card_item_minimize.view.*
import kotlinx.android.synthetic.main.card_item_minimize.view.img_profile
import kotlinx.android.synthetic.main.card_item_minimize.view.tv_fullname
import kotlinx.android.synthetic.main.card_item_minimize.view.tv_username

class ListFavoriteAdapter(private val activity: Activity):
    RecyclerView.Adapter<ListFavoriteAdapter.FavoriteViewHolder>()
{
    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFavoriteAdapter.FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_minimize, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.listFavorite.size
    }

    override fun onBindViewHolder(holder: ListFavoriteAdapter.FavoriteViewHolder, position: Int) {
        return holder.bind(listFavorite[position])
    }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite){
            with(itemView){
                tv_fullname.text = favorite.fullname
                tv_username.text = favorite.username
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .apply(
                        RequestOptions().override(
                            ListUserAdapter.IMG_WIDTH,
                            ListUserAdapter.IMG_HEIGHT
                    ))
                    .placeholder(R.drawable.baseline_account_circle_black_48)
                    .error(R.drawable.baseline_broken_image_black_48)
                    .into(img_profile)
                cv_item.setOnClickListener(CustomItemClickListener(adapterPosition,
                    object : CustomItemClickListener.OnItemClickCallback{
                        override fun onItemClicked(view: View, position: Int) {
                            val favoriteUser = listFavorite[position]
                            val user = User(
                                favoriteUser.fullname,
                                favoriteUser.username,
                                favoriteUser.avatar,
                                favoriteUser.company,
                                favoriteUser.location,
                                favoriteUser.repository,
                                favoriteUser.follower,
                                favoriteUser.following
                            )

                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_DETAIL, user)
                            context.startActivity(intent)
                        }
                    }))
            }
        }
    }

}