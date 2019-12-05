package com.example.firebasemvp.addmember.presenter

class AddMemberContract {
    interface View {
        fun addMemberSuccess()
    }

    interface Presenter {
        fun addMemberUser(useId: String?, name: String?, weight: String?, height: String?)
    }
}