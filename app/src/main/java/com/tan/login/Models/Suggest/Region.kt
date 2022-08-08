package com.tan.login.Models.Suggest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Region {
    @SerializedName("regionnamelong")
    @Expose
    val regionnamelong: String? = null

    @SerializedName("seo_url")
    @Expose
    val seoUrl: String? = null

    @SerializedName("regioncode")
    @Expose
    val regioncode: String? = null

    @SerializedName("regiontype")
    @Expose
    val regiontype: String? = null

    @SerializedName("regionnamelong_vi")
    @Expose
    val regionnamelongVi: String? = null

    @SerializedName("countrycode")
    @Expose
    val countrycode: String? = null

    @SerializedName("countryname")
    @Expose
    val countryname: String? = null

    @SerializedName("number_of_hotels")
    @Expose
    val numberOfHotels: String? = null

    @SerializedName("regionname_vi")
    @Expose
    val regionnameVi: String? = null

    @SerializedName("regionid")
    @Expose
    val regionid: String? = null

    @SerializedName("provider")
    @Expose
    val provider: String? = null

    @SerializedName("regionname")
    @Expose
    val regionname: String? = null

    @SerializedName("location")
    @Expose
    val location: Location? = null

    @SerializedName("ancestors")
    @Expose
    val ancestors: List<Ancestor>? = null

    @SerializedName("region")
    @Expose
    val region: String? = null

    @SerializedName("score")
    @Expose
    val score: Int? = null

    @SerializedName("city_seo_url")
    @Expose
    val citySeoUrl: String? = null

    @SerializedName("id")
    @Expose
    val id: String? = null
}