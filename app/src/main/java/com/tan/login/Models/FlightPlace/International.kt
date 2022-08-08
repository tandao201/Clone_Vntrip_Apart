package com.tan.login.Models.FlightPlace

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class International {

    @SerializedName("region_name")
    @Expose
    val regionName: String? = null

    @SerializedName("region_data")
    @Expose
    val regionData: List<RegionDatum__1>? = null
}