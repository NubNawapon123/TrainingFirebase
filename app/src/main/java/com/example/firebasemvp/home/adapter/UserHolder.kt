package com.example.retrofitfirebasemvp.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasemvp.home.UserCallback
import com.example.firebasemvp.home.model.UserListModel
import kotlinx.android.synthetic.main.item_member_user.view.*

class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var textViewName = itemView.tv_name
    var textViewAge = itemView.tv_age
    var itemUser = itemView.item_user

    fun bindData(
        userListModel: UserListModel?,
        callback: UserCallback?
    ) {

        textViewName.text = "Name : " + userListModel?.name
        textViewAge.text = "Age: ${userListModel?.idUser} "

        itemUser?.apply {

            setOnClickListener {
                callback?.onSelectItem(userListModel ?: UserListModel())
            }

            setOnLongClickListener {
                it.post {
                    callback?.onSelectItemLongClick(userListModel ?: UserListModel())
                }
            }
        }
    }

}