package com.example.vkfriends.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import com.example.vkfriends.R
import com.example.vkfriends.models.FriendModel
import com.example.vkfriends.views.FriendsView


class FriendsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mSourceList: ArrayList<FriendModel> = ArrayList()
    private var mFriendsList: ArrayList<FriendModel> = ArrayList()

    fun setupFriends(friendList: ArrayList<FriendModel>) {
        mSourceList.clear()
        mSourceList.addAll(friendList)
        filter(query = "")
    }

    fun filter(query: String) {
        mFriendsList.clear()
        mSourceList.forEach({
            if (it._name.contains(query, ignoreCase = true) || it.surname.contains(query, ignoreCase = true)) {
                mFriendsList.add(it)
            } else {
                it.city?.let { city -> if (city.contains(query, ignoreCase = true)) {
                    mFriendsList.add(it)
                }}
            }
        })
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendsViewHolder) {
            holder.bind(friendModel = mFriendsList[position])
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(p0?.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend, p0, false)
        return FriendsViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mFriendsList.count()
    }

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var mCivAvatar: CircleImageView = itemView.findViewById(R.id.friend_civ_avatar)
        private var mTxtUsername: TextView = itemView.findViewById(R.id.friend_txt_username)
        private var mTxtCity: TextView = itemView.findViewById(R.id.friend_txt_city)
        private var mImgOnline: View = itemView.findViewById(R.id.friend_img_online)

        @SuppressLint("SetTextI18n")
        fun bind(friendModel: FriendModel) {
            friendModel.avatar?.let { url ->
                Picasso.get().load(url)
                        .into(mCivAvatar)
            }

            mTxtUsername.text = "${friendModel._name} ${friendModel.surname}"
            mTxtCity.text = itemView.context.getString(R.string.friend_no_city)
            friendModel.city?.let { city -> mTxtCity.text = city }

            if (friendModel.isOnline) {
                mImgOnline.visibility = View.VISIBLE
            } else {
                mImgOnline.visibility = View.GONE
            }
        }
    }
}