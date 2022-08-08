package com.tan.login.Models.FlightTicket.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Departure {

    @SerializedName("total")
    @Expose
    val total: Int? = null

    @SerializedName("count")
    @Expose
    val count: Int? = null

    @SerializedName("save")
    @Expose
    val save: Double? = null

    @SerializedName("data")
    @Expose
    val data: Data? = null
}