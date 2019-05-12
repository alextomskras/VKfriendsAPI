package com.example.vkfriends.providers

import android.os.Handler
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.vk.sdk.api.*
import com.example.vkfriends.R
import com.example.vkfriends.models.FriendModel
import com.example.vkfriends.presenters.FriendsPresenter



class FriendsProvider(var presenter: FriendsPresenter) {
    private val TAG: String = FriendsProvider::class.java.simpleName

    fun testLoadFriends(hasFriends: Boolean) {
        Handler().postDelayed({
            val friendsList: ArrayList<FriendModel> = ArrayList()
            if (hasFriends) {
                val friend1 = FriendModel(_name = "Ivan", surname = "Petrov", city = null,
                        avatar = "https://upload.wikimedia.org/wikipedia/ru/8/86/Иван_Иванович_Петров_%28певец%29.jpg", isOnline = true)
                val friend2 = FriendModel(_name = "Alexey", surname = "Gladkov", city = "Tomsk",
                        avatar = "https://pp.userapi.com/c837723/v837723005/5eca5/T7p2k_hYvqw.jpg", isOnline = false)
                val friend3 = FriendModel(_name = "Egor", surname = "Sidorov", city = "Moscow",
                        avatar = "https://pbs.twimg.com/profile_images/833569201898057728/2Z1Vs7NS.jpg", isOnline = false)
                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)
            }

            presenter.friendsLoaded(friendsList = friendsList)
        }, 2000)
    }

    fun loadFriends() {
        val request = VKApi.friends().get(VKParameters.from(VKApiConst.COUNT, 300, VKApiConst.FIELDS, "sex, bdate, city, country, photo_100, online"))
        request.executeWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)

                val jsonParser = JsonParser()
                val parsedJson = jsonParser.parse(response.json.toString()).asJsonObject
                val friendsList: ArrayList<FriendModel> = ArrayList()

                parsedJson.get("response").asJsonObject.getAsJsonArray("items").forEach {
                    val city = if (it.asJsonObject.get("city") == null) {
                        null
                    } else {
                        it.asJsonObject.get("city").asJsonObject.get("title").asString
                    }

                    val friend = FriendModel(
                            _name = it.asJsonObject.get("first_name").asString,
                            surname = it.asJsonObject.get("last_name").asString,
                            city = city,
                            avatar = it.asJsonObject.get("photo_100").asString,
                            isOnline = it.asJsonObject.get("online").asInt == 1)
                    friendsList.add(friend)
                }

                presenter.friendsLoaded(friendsList = friendsList)
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.friends_error_loading)
            }
        })
    }
}