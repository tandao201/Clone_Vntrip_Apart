package com.tan.login.API

import com.tan.login.Models.Login.DataLogin
import com.tan.login.Models.Login.RefreshTokenResponse
import com.tan.login.Models.Login.RequestLogin
import com.tan.login.Models.Login.ResponseLogin
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface IUserAPI {

	@Headers("Accept: application/json")
	@POST("/core-user-service/auth/login/phone")
	fun login(@Body request: RequestLogin) : Call<ResponseLogin>

	@FormUrlEncoded
	@POST("oauth2/token/")
	suspend fun refreshToken(
		@Field("refresh_token") refreshToken: String,
		@Field("client_id") clientId: String,
		@Field("client_secret") clientSecret: String,
		@Field("grant_type") grantType: String
	): Response<DataLogin>
}