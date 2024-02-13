package com.example.githubusers.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubusers.R
import com.example.githubusers.data.UserFullInfo
import com.squareup.picasso.Picasso
import kotlin.Int

internal class UserAdapter(private val userList: ArrayList<UserFullInfo>,
                           private val clickListener: RecyclerViewClickListener?,
                           private val context: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        if (user.avatar.isEmpty()) {
            Picasso.get().cancelRequest(holder.imageView)
            holder.imageView.setImageDrawable(null)
        } else {
            Picasso.get().load(user.avatar).into(holder.imageView)
        }
        holder.textViewLogin.text = user.login
        if (user.loading) {
            holder.textViewFollowers.text = context.getString(R.string.loading)
            holder.textViewRepos.text = ""
        } else {
            holder.textViewFollowers.text =
                context.getString(R.string.followers_temp, user.followersNumber.toString())
            holder.textViewRepos.text =
                context.getString(R.string.repos_temp, user.reposNumber.toString())
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View) :
        ViewHolder(itemView), View.OnClickListener {
        var imageView: ImageView
        var textViewLogin: TextView
        var textViewFollowers: TextView
        var textViewRepos: TextView

        init {
            itemView.setOnClickListener(this)
            imageView = itemView.findViewById(R.id.image)
            textViewLogin = itemView.findViewById(R.id.login)
            textViewFollowers = itemView.findViewById(R.id.followers_number)
            textViewRepos = itemView.findViewById(R.id.repos_number)
        }

        override fun onClick(view: View?) {
            userList[bindingAdapterPosition].let {
                if (this@UserAdapter.clickListener != null)
                    this@UserAdapter.clickListener.onItemClick(it)
            }
        }
    }
}
