package com.tan.login.Activities

import android.os.Bundle
import android.util.Log
import android.webkit.WebViewFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tan.login.Fragments.ComboFragment
import com.tan.login.Fragments.HomeFragment
import com.tan.login.Fragments.LoginFragment
import com.tan.login.Models.Login.DataLogin
import com.tan.login.Models.Login.ResponseLogin
import com.tan.login.R
import com.tan.login.Repositories.UserRepo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val DATA_DB_KEY = "DATA DB"
    private val DATA_API_KEY = "DATA API"

    private val userRepo = UserRepo()
    private var dataLoginDb: DataLogin? = null
    var dataLoginApi: DataLogin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRepo.setUserDao(this)
        getDataIntent()
        if (savedInstanceState == null) {
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
}