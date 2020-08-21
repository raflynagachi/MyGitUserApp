package com.dicoding.picodiploma.mygituserapp.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class NoteColumns: BaseColumns{
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val USERNAME = "username"
            const val FULLNAME = "fullname"
            const val AVATAR_URL = "avatar_url"
        }
    }
}