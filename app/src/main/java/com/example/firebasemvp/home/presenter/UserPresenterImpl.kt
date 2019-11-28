package com.example.firebasemvp.home.presenter

import com.example.firebasemvp.home.model.UserListModel
import com.example.firebasemvp.home.model.UserModel
import com.google.firebase.database.*


class UserPresenterImpl(var view: UserContact.View?) : UserContact.Presenter {

    companion object {
        const val KEY_USERS = "users"
        const val KEY_USERS_LIST = "userList"
        const val KEY_ID_USER = "idUser"
    }

    private var database = FirebaseDatabase.getInstance()
    private var refUsersChild = database.getReference(KEY_USERS)
    private var refUserListChild = database.getReference(KEY_USERS).child(KEY_USERS_LIST)

    private var listUser: UserModel? = null

    override fun loadData() {
        addDataDefault()
        userListEvent()
        view?.updateData(listUser ?: UserModel())
    }

    override fun removeItemMember(userId: String) {
        val queryIdUser = refUserListChild.orderByChild(KEY_ID_USER).equalTo(userId)
        queryIdUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    it.ref.removeValue()
                }
            }

        })
    }

    private fun addDataDefault() {
        val userModel = UserModel().apply {
            email = "nawapon.f@kbtg.tech"
            userList = arrayListOf(
                UserListModel("1", "nub1", "150", "50"),
                UserListModel("3", "nub2", "160", "50"),
                UserListModel("5", "nub3", "170", "50"),
                UserListModel("7", "nub3", "170", "50"),
                UserListModel("7", "nub3", "170", "50")
            )
        }
        listUser = userModel
        refUsersChild.setValue(listUser)
    }

    private fun userListEvent() {
        refUserListChild.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, value: String?) {}
            override fun onChildChanged(dataSnapshot: DataSnapshot, value: String?) {}
            override fun onChildAdded(dataSnapshot: DataSnapshot, value: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(UserListModel::class.java)
                listUser?.userList?.remove(value)
                listUser?.userList?.let { list ->
                    view?.updateList(list)
                }
            }

        })
    }

}