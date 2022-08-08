package com.tan.login.Models.FlightPlace

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Domestic {

    @SerializedName("region_name")
    @Expose
    val regionName: String? = null

    @SerializedName("region_data")
    @Expose
    val regionData: List<RegionDatum>? = null
}