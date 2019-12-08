package com.example.firebasemvp.home

import com.example.firebasemvp.home.model.UserListModel

interface UserCallback {
    fun onSelectItem(userListModel: UserListModel?)
    fun onSelectItemLongClick(userListModel: UserListModel?)
}