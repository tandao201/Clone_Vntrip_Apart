package com.tan.login.Activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tan.login.Fragments.HomeFragment
import com.tan.login.Fragments.LoginFragment
import com.tan.login.Models.Login.DataLogin
import com.tan.login.R
import com.tan.login.Repositories.UserRepo
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    private val DATA_DB_KEY = "DATA DB"
    private val DATA_API_KEY = "DATA API"

    private val TAG = "MainActivity"

    private val userRepo = UserRepo()
    private var dataLoginDb: DataLogin? = null
    var dataLoginApi: DataLogin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRepo.setUserDao(this)
        getDataIntent()
        if (savedInstanceState == null) {
            hasUserLogined()
        }
    }

    private fun hasUserLogined() {
        if (dataLoginDb != null) {
            if (dataLoginApi != null) {
                Log.e("Main activity", "dataLoginApi khac null")
                var homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.root_container, homeFragment).commit()
            } else {
                Log.e("Main activity", "dataLoginApi null")
                openLoginFragment()
            }
        } else {
            Log.e("Main activity", "dataLoginDb null")
            openLoginFragment()
        }
    }

    private fun getDataIntent() {
        val intent = intent
        if (intent != null) {
            if (intent.hasExtra(DATA_DB_KEY)) {
                dataLoginDb = intent.getSerializableExtra(DATA_DB_KEY) as DataLogin?
            }
            if (intent.hasExtra(DATA_API_KEY)) {
                dataLoginApi = intent.getSerializableExtra(DATA_API_KEY) as DataLogin?
            }
        }
    }

    private fun openLoginFragment() {
        var loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.root_container, loginFragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    fun onMessageEvent(itemClicked: String?) {
        Log.e(TAG, "onMessageEvent: ${itemClicked!!}")
    }
}