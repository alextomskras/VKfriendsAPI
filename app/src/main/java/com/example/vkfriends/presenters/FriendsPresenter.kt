package com.example.vkfriends.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vkfriends.R
import com.example.vkfriends.models.FriendModel
import com.example.vkfriends.providers.FriendsProvider
import com.example.vkfriends.views.FriendsView
import com.example.vkfriends.views.LoginView



@InjectViewState
class FriendsPresenter: MvpPresenter<FriendsView>() {
    fun loadFriends() {
        viewState.startLoading()
        FriendsProvider(presenter = this).loadFriends()
    }

    fun friendsLoaded(friendsList: ArrayList<FriendModel>) {
        viewState.endLoading()
        if (friendsList.size == 0) {
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.friends_no_items)
        } else {
            viewState.setupFriendsList(friendsList = friendsList)
        }
    }

    fun showError(textResource: Int) {
        viewState.showError(textResource = textResource)
    }
}