package com.tan.login.Models.FlightPlace

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class RegionDatum: Serializable {

    constructor(code: String, provinceName: String) {
        this.code = code
        this.provinceName = provinceName
    }

    @SerializedName("id")
    @Expose
    val id: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("province_id")
    @Expose
    val provinceId: Int? = null

    @SerializedName("created_at")
    @Expose
    val createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String? = null

    @SerializedName("type")
    @Expose
    val type: String? = null

    @SerializedName("region")
    @Expose
    val region: Int? = null

    @SerializedName("province_name")
    @Expose
    var provinceName: String? = null

    @SerializedName("has_in_atadi")
    @Expose
    val hasInAtadi: Boolean? = null

    @SerializedName("region_name")
    @Expose
    val regionName: String? = null

    @SerializedName("region_name_vi")
    @Expose
    val regionNameVi: String? = null

    var isSelected: Boolean = false
}