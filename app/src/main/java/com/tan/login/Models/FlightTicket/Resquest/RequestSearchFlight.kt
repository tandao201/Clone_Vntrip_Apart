package com.tan.login.Models.FlightTicket.Resquest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class RequestSearchFlight(
    val adultCount: Int,

    val childCount: Int,

    val infantCount: Int,

    val listFlight: List<ListFlight> ) : Serializable {

}