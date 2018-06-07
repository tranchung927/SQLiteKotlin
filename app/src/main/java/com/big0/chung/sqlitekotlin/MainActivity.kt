package com.big0.chung.sqlitekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usersDBHelper = UsersDBHelper(this)

    }

    fun createUser(view: View) {
        val userId = edittext_userid.text.toString()
        val name = edittext_name.text.toString()
        val age = edittext_age.text.toString()
        val user = UserModel(userId, name, age)
        val result = usersDBHelper.insertUser(user)
        if (result) {
            edittext_userid.setText("")
            edittext_name.setText("")
            edittext_age.setText("")
        }
    }

    fun showAllUsers(view: View) {
        usersDBHelper.readAllUsers().forEach {
            println(it.name + " : " + it.userid)
        }
    }

    fun findUser(view: View) {
        val userId = edittext_userid.text.toString()
        val userFind = usersDBHelper.readUser(userId)
        if (userFind != null) {
            println(userFind.name)
        }
    }
}
