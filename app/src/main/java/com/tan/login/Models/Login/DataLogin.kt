package com.tan.login.Models.Login

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "data_login")
class DataLogin : Serializable {

	@PrimaryKey(autoGenerate = true)
	var id: Int = 0
	@SerializedName("access_token")
	@Expose
	var accessToken: String? = null

	@SerializedName("refresh_token")
	@Expose
	var refreshToken: String? = null

	@SerializedName("expires_in")
	@Expose
	var expiresIn: Int? = null

	@SerializedName("scope")
	@Expose
	var scope: String? = null

	@SerializedName("token_type")
	@Expose
	var tokenType: String? = null

//	var createAt: Date? = null
}