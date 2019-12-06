package com.example.firebasemvp.home.presenter

import com.example.firebasemvp.home.model.UserListModel
import com.example.firebasemvp.home.model.UserModel
import com.example.firebasemvp.home.model.UserResponseModel
import com.google.firebase.database.*


class UserPresenterImpl(var view: UserContract.View?) : UserContract.Presenter {

    companion object {
        const val KEY_USERS = "users"
        const val KEY_USERS_LIST = "userList"
        const val KEY_ID_USER = "idUser"
    }

    private var database = FirebaseDatabase.getInstance()
    private var refUsersChild = database.getReference(KEY_USERS)
    private var refUserListChild = database.getReference(KEY_USERS).child(KEY_USERS_LIST)

    private var listUser: UserModel? = null

    override fun addDefaultData() {
        val userModel = UserModel().apply {
            email = "nawapon.f@kbtg.tech"
            userList = arrayListOf(
                UserListModel("1", "nub1", "150", "50"),
                UserListModel("3", "nub2", "160", "60"),
                UserListModel("5", "nub3", "170", "60"),
                UserListModel("7", "nub3", "170", "65"),
                UserListModel("7", "nub3", "170", "62")
            )
        }
        listUser = userModel
        refUsersChild.setValue(listUser)
    }

    override fun loadDataFormFirebase() {
        refUsersChild.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(UserResponseModel::class.java)
                val model = UserModel()
                model.email = value?.email
                value?.userList?.map {
                    if (!it?.value.idUser.isNullOrEmpty()) {
                        val modelList = UserListModel().apply {
                            idUser = it?.value?.idUser
                            name = it?.value?.name
                            weight = it?.value?.weight
                            height = it?.value?.height
                        }
                        model?.userList?.add(modelList)
                    }
                }
                model?.email = value?.email
                listUser = model
                /*val value = dataSnapshot.getValue(UserModel::class.java)
                val model = UserModel()
                value?.userList?.map { userListModel ->
                    if (!userListModel?.idUser.isNullOrEmpty()) {
                        model?.userList?.add(userListModel)
                    }
                }
                model?.email = value?.email
                listUser = model*/
                view?.updateData(listUser ?: UserModel())
            }

        })
        initEventUserList()
    }

    override fun removeItemMember(userId: String) {
        val queryIdUser = refUserListChild.orderByChild(KEY_ID_USER).equalTo(userId)
        queryIdUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    it.ref.removeValue()
                }
            }
        })
    }

    private fun initEventUserList() {
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