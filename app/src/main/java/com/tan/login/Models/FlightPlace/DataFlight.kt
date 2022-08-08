package com.tan.login.Models.FlightPlace

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class DataFlight {

    @SerializedName("domestic")
    @Expose
    var domestic: List<Domestic?>? = null

    @SerializedName("international")
    @Expose
    var international: List<International?>? = null

}