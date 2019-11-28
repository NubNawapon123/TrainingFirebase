package com.example.firebasemvp.home.presenter

import com.example.firebasemvp.common.BaseView
import com.example.firebasemvp.home.model.UserListModel
import com.example.firebasemvp.home.model.UserModel

class UserContact {
    interface Presenter {


        fun loadData()

        fun removeItemMember(userId: String)
    }

    interface View : BaseView {

        fun updateData(model: UserModel)

        fun updateList(model: List<UserListModel>)

    }
}