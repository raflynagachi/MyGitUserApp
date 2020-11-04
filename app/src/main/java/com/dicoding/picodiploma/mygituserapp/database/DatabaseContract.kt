package com.dicoding.picodiploma.mygituserapp.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class FavoriteColumns: BaseColumns{
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val USERNAME = "username"
            const val FULLNAME = "fullname"
            const val AVATAR_URL = "avatar_url"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val REPOSITORY = "repository"
            const val FOLLOWER = "follower"
            const val FOLLOWING = "following"
        }
    }
}