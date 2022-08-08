package com.tan.login.Models.HotelSearch.showPrice

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FinalPrice {

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("discount_type")
    @Expose
    var discount_type: String? = null

    @SerializedName("incl_discount_value")
    @Expose
    var incl_discount_value: Int? = null

    @SerializedName("excl_discount_value")
    @Expose
    var excl_discount_value: Int? = null

    @SerializedName("incl_vat_fee_price")
    @Expose
    var incl_vat_fee_price: Int? = null

    @SerializedName("excl_vat_fee_price")
    @Expose
    var excl_vat_fee_price: Int? = null

    @SerializedName("order")
    @Expose
    var order: Int? = null

    @SerializedName("show")
    @Expose
    var show: Boolean? = null
}