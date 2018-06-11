package com.big0.chung.sqlitekotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import kotlinx.android.synthetic.main.all_users_activity.*

class MainActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    var users = ArrayList<UserModel>()
    private val adapter by lazy { UserAdapter(this, users) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_users_activity)
        usersDBHelper = UsersDBHelper(this)
        all_users_recycler_view.layoutManager = LinearLayoutManager(this)
        all_users_recycler_view.setHasFixedSize(true)
        getAllUsers {
            users = it
            all_users_recycler_view.adapter = adapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                addNewUser()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addNewUser() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("New User")
        val view = layoutInflater.inflate(R.layout.dialog_new_user, null)
        builder.setView(view)
        builder.setPositiveButton("Create") {dialog, _ ->
            val userIDEditText = view.findViewById<EditText>(R.id.user_id_editText)
            val userNameEditText = view.findViewById<EditText>(R.id.user_name_editText)
            val userAgeEditText = view.findViewById<EditText>(R.id.user_age_editText)
            val user = UserModel(userIDEditText.text.toString(), userNameEditText.text.toString(), userAgeEditText.text.toString())
            val isInsert = usersDBHelper.insertUser(user)
            if (isInsert) {
                users.add(user)
                adapter.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    fun getAllUsers(complete: (ArrayList<UserModel>)->Unit) {
        complete(usersDBHelper.readAllUsers())
    }
}
