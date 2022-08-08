package com.tan.login.Models.HotelSearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GroupFacilities {

    @SerializedName("group_name")
    @Expose
    var group_name: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("group_order")
    @Expose
    var group_order:Int? = null

    @SerializedName("key")
    var key: String? = null
}