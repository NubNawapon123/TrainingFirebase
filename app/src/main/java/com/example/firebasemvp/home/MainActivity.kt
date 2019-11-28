package com.example.firebasemvp.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasemvp.R
import com.example.firebasemvp.common.BaseActivity
import com.example.firebasemvp.home.adapter.UserAdapter
import com.example.firebasemvp.home.model.UserListModel
import com.example.firebasemvp.home.model.UserModel
import com.example.firebasemvp.home.presenter.UserContact
import com.example.firebasemvp.home.presenter.UserPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), UserContact.View {

    private lateinit var presenter: UserPresenterImpl
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = UserPresenterImpl(this@MainActivity)
        presenter.loadData()
    }

    override fun updateData(model: UserModel) {
        tv_email?.text = model.email
        userAdapter = UserAdapter(model.userList, object : UserCallback {
            override fun onSelectItem(userListModel: UserListModel) {

            }

            override fun onSelectItemLongClick(userListModel: UserListModel) {
                presenter.removeItemMember(userListModel.idUser ?: "")
            }
        })

        rv_user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

        btn_add_member.setOnClickListener {
            presenter.loadData()
        }
    }

    override fun updateList(model: List<UserListModel>) {
        userAdapter.updateDataUserList(model)
    }


}
