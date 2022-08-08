package com.tan.login.Models.HotelSearch.showPrice

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ShowPrice {

    @SerializedName("promotion")
    @Expose
    var promotion: Promotion? = null

    @SerializedName("member_discount")
    @Expose
    var member_discount: MemberDiscount? = null

    @SerializedName("mobile_rate")
    @Expose
    var mobile_rate: MobileRate? = null

    @SerializedName("loyalty_discount")
    @Expose
    var loyalty_discount:LoyaltyDiscount? = null

    @SerializedName("discount_coupon")
    @Expose
    var discount_coupon: DiscountCoupon? = null

    @SerializedName("final_price")
    @Expose
    var final_price: FinalPrice? = null
}