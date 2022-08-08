package com.tan.login.Models.Login

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseLogin :Serializable {
	@SerializedName("status")
	@Expose
	var status: String? = null

	@SerializedName("message")
	@Expose
	var message: String? = null

	@SerializedName("data")
	@Expose
	var dataLogin: DataLogin? = null
}