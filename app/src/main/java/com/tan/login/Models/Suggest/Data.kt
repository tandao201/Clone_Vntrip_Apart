package com.tan.login.Models.Suggest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Data {
    @SerializedName("regions")
    @Expose
    var regions: List<Region?>? = null

    @SerializedName("hotels")
    @Expose
    var hotels: List<Hotel?>? = null

}