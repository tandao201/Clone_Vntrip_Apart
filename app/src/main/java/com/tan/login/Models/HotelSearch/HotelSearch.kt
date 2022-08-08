package com.tan.login.Models.HotelSearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tan.login.Models.HotelSearch.showPrice.ShowPrice

class HotelSearch {

    @SerializedName("vntrip_id")
    @Expose
    var vntrip_id:String? = null

    @SerializedName("provider")
    var provider: String? = null

    @SerializedName("checkin_date")
    @Expose
    var checkin_date: String? = null

    @SerializedName("nights")
    @Expose
    var nights: Int? = null

    @SerializedName("rooms_available")
    @Expose
    var rooms_available: Int? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name_vi")
    @Expose
    var name_vi: String? = null

    @SerializedName("full_address")
    @Expose
    var full_address: String? = null

    @SerializedName("province_id")
    @Expose
    var province_id:Int? = null

    @SerializedName("star_rate")
    @Expose
    var star_rate: Double? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("groupFacilities")
    @Expose
    var groupFacilities: List<GroupFacilities>? = null

    @SerializedName("review_point")
    @Expose
    var review_point: Double? = null

    @SerializedName("price_one_night")
    @Expose
    var price_one_night: Int? = null

    @SerializedName("thumb_image")
    @Expose
    var thumb_image: String? = null

    @SerializedName("show_prices")
    @Expose
    var show_price: ShowPrice ? = null

    @SerializedName("tags")
    @Expose
    var tags: List<String>? = null

    @SerializedName("have_360img")
    @Expose
    var have_360img: Boolean? = null

    @SerializedName("is_special")
    @Expose
    var is_special: Boolean? = null
}