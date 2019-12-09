package com.example.firebasemvp.home.presenter

import com.example.firebasemvp.common.BaseView
import com.example.firebasemvp.home.model.UserListModel
import com.example.firebasemvp.home.model.UserModel

class UserContract {
    interface Presenter {

        fun addDefaultEmail()

        fun loadDataFormFirebase()

        fun removeItemMember(userId: String)
    }

    interface View : BaseView {

        fun updateData(model: UserModel)

        fun updateList(model: List<UserListModel>)

    }
}