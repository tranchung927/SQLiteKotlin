package com.big0.chung.sqlitekotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.user_cell.view.*

class UserAdapter(var context: Context, var users: ArrayList<UserModel>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
        val cell = UserViewHolder(view.inflate(R.layout.user_cell, parent, false))
        return cell
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(users[position])
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(user: UserModel) {
            itemView.user_id_textView.text = user.userid
            itemView.name_textView.text = user.name
            itemView.age_textView.text = user.age
        }
    }
}