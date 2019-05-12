package com.example.vkfriends.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by sunwi on 09.11.2017.
 */

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface LoginView: MvpView {
    fun startLoading()
    fun endLoading()
    fun showError(textResource: Int)
    fun openFriends()
}