package br.com.tech.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tech.R
import br.com.tech.feture.data.User
import kotlinx.android.synthetic.main.adapter_user.view.*

class UserAdapter(delegate: Delegate) : BaseAdapter<User>(delegate = delegate) {


    class UserViewHolder(itemView: View) : BaseViewHolder<User>(itemView) {

        @SuppressLint("SetTextI18n")
        override fun bind(data: User) {
            itemView.user_name.text = "${data.firstName} ${data.lastName}"
            itemView.user_email.text = data.email
        }

    }

    override fun getViewHolder(parent: ViewGroup): BaseViewHolder<User> {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_user,
                parent,
                false
            )
        )
    }
}