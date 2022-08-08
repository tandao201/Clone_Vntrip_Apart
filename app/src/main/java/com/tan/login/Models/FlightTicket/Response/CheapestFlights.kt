package com.tan.login.Models.FlightTicket.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CheapestFlights {

    @SerializedName("00->08")
    @Expose
    val _0008: CheapestFlightItem? = null

    @SerializedName("08->12")
    @Expose
    val _0812: CheapestFlightItem? = null

    @SerializedName("12->18")
    @Expose
    val _1218: CheapestFlightItem? = null

    @SerializedName("18->24")
    @Expose
    val _1824: CheapestFlightItem? = null
}