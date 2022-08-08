package com.tan.login.Models.FlightTicket.Resquest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ListFlight(val departDate: String,

                 val endPoint: String,

                 val startPoint: String): Serializable {


}