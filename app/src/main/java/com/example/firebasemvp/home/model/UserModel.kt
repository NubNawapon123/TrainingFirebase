package com.example.firebasemvp.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var email: String? = "",
    var userList: ArrayList<UserListModel> = arrayListOf()
) : Parcelable

@Parcelize
data class UserListModel(
    var idUser: String? = "",
    var name: String? = "",
    var weight: String? = "",
    var height: String? = "",
    var bmi: String? = ""
) : Parcelable