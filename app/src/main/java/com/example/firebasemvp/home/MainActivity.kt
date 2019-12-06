package com.example.firebasemvp.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasemvp.R
import com.example.firebasemvp.addmember.AddMemberActivity
import com.example.firebasemvp.common.BaseActivity
import com.example.firebasemvp.home.adapter.UserAdapter
import com.example.firebasemvp.home.model.UserListModel
import com.example.firebasemvp.home.model.UserModel
import com.example.firebasemvp.home.presenter.UserContract
import com.example.firebasemvp.home.presenter.UserPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), UserContract.View {

    companion object {
        const val REQUEST_CODE = 999
    }
    private lateinit var presenter: UserPresenterImpl
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = UserPresenterImpl(this@MainActivity)
        presenter.loadDataFormFirebase()
        initView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.loadDataFormFirebase()
        }

    }

    override fun updateData(model: UserModel) {
        tv_email?.text = model.email
        userAdapter = UserAdapter(model.userList, object : UserCallback {
            override fun onSelectItem(userListModel: UserListModel) {
                startActivityForResult(
                    Intent(AddMemberActivity.getStartIntent(this@MainActivity, userListModel)),
                    REQUEST_CODE
                )
            }

            override fun onSelectItemLongClick(userListModel: UserListModel) {
                presenter.removeItemMember(userListModel.idUser ?: "")
            }
        })

        rv_user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    override fun updateList(model: List<UserListModel>) {
        userAdapter.updateDataUserList(model)
    }

    private fun initView() {
        btn_add_member_firebase.setOnClickListener {
            presenter.loadDataFormFirebase()
        }

        btn_add_member_data.setOnClickListener {
            startActivityForResult(
                Intent(this, AddMemberActivity::class.java), REQUEST_CODE
            )
        }
    }

}
