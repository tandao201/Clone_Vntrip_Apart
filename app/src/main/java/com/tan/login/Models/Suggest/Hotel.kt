package com.tan.login.Models.Suggest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Hotel {
    @SerializedName("starrating")
    @Expose
    val starrating: Double? = null

    @SerializedName("code")
    @Expose
    val code: String? = null

    @SerializedName("address")
    @Expose
    val address: String? = null

    @SerializedName("name_vi")
    @Expose
    val nameVi: String? = null

    @SerializedName("check_in_time")
    @Expose
    val checkInTime: String? = null

    @SerializedName("countrycode")
    @Expose
    val countrycode: String? = null

    @SerializedName("latitude")
    @Expose
    val latitude: String? = null

    @SerializedName("countryname")
    @Expose
    val countryname: String? = null

    @SerializedName("check_out_time")
    @Expose
    val checkOutTime: String? = null

    @SerializedName("countryid")
    @Expose
    val countryid: Int? = null

    @SerializedName("active_status")
    @Expose
    val activeStatus: String? = null

    @SerializedName("provider")
    @Expose
    val provider: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("address_en")
    @Expose
    val addressEn: String? = null

    @SerializedName("vnt_ext_data")
    @Expose
    val vntExtData: VntExtData? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("propertycurrency")
    @Expose
    val propertycurrency: String? = null

    @SerializedName("longitude")
    @Expose
    val longitude: String? = null
}