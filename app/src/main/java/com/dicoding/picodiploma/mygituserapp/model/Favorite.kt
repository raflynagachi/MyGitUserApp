package com.dicoding.picodiploma.mygituserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
    var fullname: String?="",
    var username: String?="",
    var avatar: String?=""
) : Parcelable