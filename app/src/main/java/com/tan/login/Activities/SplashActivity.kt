package com.tan.login.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tan.login.Models.Login.DataLogin
import com.tan.login.Repositories.UserRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

	private val DATA_DB_KEY = "DATA DB"
	private val DATA_API_KEY = "DATA API"

	private val userRepo = UserRepo()
	private var dataLoginDb: DataLogin? = null
	private var dataLoginApi: DataLogin? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		userRepo.setUserDao(this)
		lifecycleScope.launch {
			getLatestDatalogin()
			var intent = Intent(this@SplashActivity, MainActivity::class.java)
			if (dataLoginDb!= null) {
				getDataApi()
				if (dataLoginApi!=null)	userRepo.addDataLogin(dataLoginApi!!)
				intent.putExtra(DATA_DB_KEY,dataLoginDb)
				intent.putExtra(DATA_API_KEY,dataLoginApi)
			}
			startActivity(intent)
			finish()
		}
	}

	private suspend fun getLatestDatalogin() {
		dataLoginDb = userRepo.getDataloginFromDb()
		if (dataLoginDb!= null){
			Log.e("Splash Actity db",dataLoginDb?.refreshToken!!)
		}
	}

	private suspend fun getDataApi() {
		val response = userRepo.refreshToken(dataLoginDb?.refreshToken!!)
		if (response.isSuccessful) {
			dataLoginApi = response.body()
			Log.e("Splash Actity api",dataLoginApi?.refreshToken!!)
		}
	}
}