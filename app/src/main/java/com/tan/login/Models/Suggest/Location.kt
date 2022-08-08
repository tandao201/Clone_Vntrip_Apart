package com.tan.login.Models.Suggest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Location {
    @SerializedName("lon")
    @Expose
    val lon: Double? = null

    @SerializedName("lat")
    @Expose
    val lat: Double? = null
}