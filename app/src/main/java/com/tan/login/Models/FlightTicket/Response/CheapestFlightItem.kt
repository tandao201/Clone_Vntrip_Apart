package com.tan.login.Models.FlightTicket.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CheapestFlightItem {

    @SerializedName("fromTime")
    @Expose
    val fromTime: String? = null

    @SerializedName("toTime")
    @Expose
    val toTime: String? = null

    @SerializedName("departure")
    @Expose
    val departure: Departure? = null

    @SerializedName("arrival")
    @Expose
    val arrival: Arrival? = null
}