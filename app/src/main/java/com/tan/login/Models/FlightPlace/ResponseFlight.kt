package com.tan.login.Models.FlightPlace

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ResponseFlight {

    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("message")
    @Expose
    val message: String? = null

    @SerializedName("data")
    @Expose
    val data: DataFlight? = null
}