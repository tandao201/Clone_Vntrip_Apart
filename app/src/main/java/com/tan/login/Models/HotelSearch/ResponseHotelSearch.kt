package com.tan.login.Models.HotelSearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseHotelSearch {

    @SerializedName ("searchId")
    @Expose
    var searchId: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("limit")
    @Expose
    var limit: Int? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null

    @SerializedName("total_pages")
    @Expose
    var total_pages: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<HotelSearch>? = null
}