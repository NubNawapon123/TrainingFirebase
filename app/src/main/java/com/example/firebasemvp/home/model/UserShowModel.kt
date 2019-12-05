package com.example.firebasemvp.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserShowModel(
    var email: String? = "",
    var userList: Map<String, UserListShowModel> = mapOf()
) : Parcelable

@Parcelize
data class UserListShowModel(
    var idUser: String? = "",
    var name: String? = "",
    var weight: String? = "",
    var height: String? = "",
    var bmi: String? = ""
) : Parcelable