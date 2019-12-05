package com.example.firebasemvp.addmember

import android.app.Activity
import android.os.Bundle
import com.example.firebasemvp.R
import com.example.firebasemvp.addmember.presenter.AddMemberContract
import com.example.firebasemvp.addmember.presenter.AddMemberPresenterImpl
import com.example.firebasemvp.common.BaseActivity
import kotlinx.android.synthetic.main.activity_add_member.*

class AddMemberActivity : BaseActivity(), AddMemberContract.View {

    private lateinit var presenter: AddMemberPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        presenter = AddMemberPresenterImpl(this@AddMemberActivity)
        initView()
    }

    override fun addMemberSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun initView() {
        btn_add_member?.setOnClickListener {
            presenter.addMemberUser(
                useId = edt_id?.text?.trim().toString(),
                name = edt_name?.text?.trim().toString(),
                weight = edt_weight?.text?.trim().toString(),
                height = edt_height?.text?.trim().toString()
            )
        }
    }
}