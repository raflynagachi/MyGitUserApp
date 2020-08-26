package com.dicoding.picodiploma.mygituserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
    var fullname: String?="",
    var username: String?="",
    var avatar: String?="",
    var company: String?="",
    var location: String?="",
    var repository: String?="",
    var follower: String?="",
    var following: String?=""
) : Parcelable