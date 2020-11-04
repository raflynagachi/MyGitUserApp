package com.dicoding.picodiploma.mygituserapp.database.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWER
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.FULLNAME
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.dicoding.picodiploma.mygituserapp.database.DatabaseContract.FavoriteColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "dbgituserapp"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                "(${USERNAME} TEXT PRIMARY KEY, "+
                "${FULLNAME} TEXT NOT NULL, "+
                "${AVATAR_URL} TEXT NOTE NULL, " +
                "${COMPANY} TEXT NOT NULL, " +
                "${LOCATION} TEXT NOT NULL, " +
                "${REPOSITORY} TEXT NOT NULL, " +
                "${FOLLOWER} TEXT NOT NULL, " +
                "${FOLLOWING} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }
}