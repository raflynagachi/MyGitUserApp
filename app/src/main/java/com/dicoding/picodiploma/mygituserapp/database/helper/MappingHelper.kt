package com.dicoding.picodiploma.mygituserapp.database.helper

import android.database.Cursor
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract
import com.dicoding.picodiploma.mygituserapp.model.Favorite

object MappingHelper{
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<Favorite>{
        val favList = ArrayList<Favorite>()

        cursor?.apply {
            while (moveToNext()){
                val fullname = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FULLNAME))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR_URL))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPOSITORY))
                val follower = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWER))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING))
                favList.add(Favorite(fullname, username, avatar, company, location, repository, follower, following))
            }
        }
        return favList
    }
}