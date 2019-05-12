package com.example.vkfriends.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.example.vkfriends.R
import com.example.vkfriends.presenters.LoginPresenter
import com.example.vkfriends.views.LoginView
import com.vk.sdk.util.VKUtil



class LoginActivity : MvpAppCompatActivity(), LoginView {
    private val TAG: String = LoginActivity::class.java.simpleName
    private lateinit var mTxtHello: TextView
    private lateinit var mBtnEnter: Button
    private lateinit var mCpvWait: CircularProgressView

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mTxtHello = findViewById(R.id.txt_login_hello)
        mBtnEnter = findViewById(R.id.btn_login_enter)
        mCpvWait = findViewById(R.id.cpv_login)

        mBtnEnter.setOnClickListener {
            VKSdk.login(this@LoginActivity, VKScope.FRIENDS)
        }

//        val fingerprints = VKUtil.getCertificateFingerprint(this, this.packageName)
//        Log.e(TAG, "fingerprint ${fingerprints[0]}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!loginPresenter.loginVk(requestCode = requestCode, resultCode = resultCode, data = data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startLoading() {
        mBtnEnter.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mBtnEnter.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun openFriends() {
        startActivity(Intent(applicationContext, FriendsActivity::class.java))
    }
}
