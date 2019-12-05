package com.example.firebasemvp.addmember.presenter

import com.example.firebasemvp.home.model.UserListModel
import com.example.firebasemvp.home.presenter.UserPresenterImpl
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AddMemberPresenterImpl(private val view: AddMemberContract.View) :
    AddMemberContract.Presenter {
    companion object {
        const val KEY_USERS = "users"
        const val KEY_USERS_LIST = "userList"
    }

    private var database = FirebaseDatabase.getInstance()
    private var refUserListChild = database.getReference(KEY_USERS).child(KEY_USERS_LIST)

    override fun addMemberUser(
        useId: String?,
        name: String?,
        weight: String?,
        height: String?
    ) {
        if (!useId.isNullOrBlank()) {
            val queryIdUser = refUserListChild.orderByChild(UserPresenterImpl.KEY_ID_USER)
            queryIdUser.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var sizeList = 0
                    dataSnapshot.children.forEachIndexed { index, dataSnapshot ->
                        dataSnapshot.ref
                        sizeList += 1
                    }
                    val map: MutableMap<String, Any> = HashMap()
                    map["$useId"] = UserListModel(useId, name, weight, height)
                    refUserListChild.updateChildren(map)
                    view.addMemberSuccess()
                }
            })
        }
    }

}