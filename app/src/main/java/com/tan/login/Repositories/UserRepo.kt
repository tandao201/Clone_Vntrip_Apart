package com.tan.login.Repositories

import android.content.Context
import android.util.Log
import com.tan.login.API.IUserAPI
import com.tan.login.API.RetrofitHelper
import com.tan.login.API.RetrofitHelperAuth
import com.tan.login.DB.UserDB
import com.tan.login.Dao.IUserDao
import com.tan.login.Models.Login.DataLogin
import com.tan.login.Models.Login.RefreshTokenResponse
import com.tan.login.Models.Login.RequestLogin
import com.tan.login.Models.Login.ResponseLogin
import retrofit2.Call
import retrofit2.Response
import retrofit2.create

class UserRepo {

    private val TOKEN_TYPE = "Bearer"
    private val CLIENT_ID = "16GuKzV8K1@92YcLg85uR5ku;peVriRZSn!1.UTh"
    private val CLIENT_SECRET =
        "TCuMmpT!EGz5UT7GE3D?s-ikA5i0sCV2pI7cFYqc!0c;z1oIyCeLsVb_ZDRdI7KOg4Pem7XKz4UU0yJ2K37I5;3Sp2UVw!tNK-ps4vaguqr09MopDwB_7larJWAmXHyv"
    private val GRANT_REFRESH_TYPE = "refresh_token"

    private val loginApi: IUserAPI = RetrofitHelper.getInstance().create(IUserAPI::class.java)
    private val authApi = RetrofitHelperAuth.getInstance().create(IUserAPI::class.java)
    private var userDao: IUserDao? = null

    fun setUserDao(context: Context){
        this.userDao = UserDB.getDatabase(context).userDao()
    }

    fun login(request: RequestLogin): Call<ResponseLogin> {
        return loginApi.login(request)
    }

    suspend fun refreshToken(token: String) : Response<DataLogin> {
        return authApi.refreshToken(token,CLIENT_ID,CLIENT_SECRET,GRANT_REFRESH_TYPE)
    }

    suspend fun getDataloginFromDb(): DataLogin? {
        return userDao?.getTopData()
    }

    suspend fun addDataLogin(dataLogin: DataLogin?) {
        return userDao!!.insertDataLogin(dataLogin)
    }
}