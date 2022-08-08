package com.tan.login.Models.Suggest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class VntExtData {
    @SerializedName("province_id")
    @Expose
    val provinceId: Int? = null

    @SerializedName("area_id")
    @Expose
    val areaId: Int? = null

    @SerializedName("type")
    @Expose
    val type: String? = null

    @SerializedName("city_id")
    @Expose
    val cityId: Int? = null
}