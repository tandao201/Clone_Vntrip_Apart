package com.tan.login.Models.FlightTicket.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ResponseSearchFlight {

    @SerializedName("flightType")
    @Expose
    val flightType: String? = null

    @SerializedName("session")
    @Expose
    val session: String? = null

    @SerializedName("itinerary")
    @Expose
    val itinerary: Int? = null

    @SerializedName("listFareData")
    @Expose
    val listFareData: List<ListFareDatum>? = null

    @SerializedName("cheapestFlights")
    @Expose
    val cheapestFlights: CheapestFlights? = null

    @SerializedName("message")
    @Expose
    val message: String? = null

    @SerializedName("status")
    @Expose
    val status: Boolean? = null
}