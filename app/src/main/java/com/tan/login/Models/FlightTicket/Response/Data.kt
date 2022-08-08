package com.tan.login.Models.FlightTicket.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("fareToken")
    @Expose
    val fareToken: String? = null

    @SerializedName("fareDataId")
    @Expose
    val fareDataId: Int? = null

    @SerializedName("airline")
    @Expose
    val airline: String? = null

    @SerializedName("itinerary")
    @Expose
    val itinerary: Int? = null

    @SerializedName("leg")
    @Expose
    val leg: Int? = null

    @SerializedName("promo")
    @Expose
    val promo: Boolean? = null

    @SerializedName("currency")
    @Expose
    val currency: String? = null

    @SerializedName("system")
    @Expose
    val system: Any? = null

    @SerializedName("adt")
    @Expose
    val adt: Int? = null

    @SerializedName("chd")
    @Expose
    val chd: Int? = null

    @SerializedName("inf")
    @Expose
    val inf: Int? = null

    @SerializedName("fareAdt")
    @Expose
    val fareAdt: Int? = null

    @SerializedName("fareChd")
    @Expose
    val fareChd: Int? = null

    @SerializedName("fareInf")
    @Expose
    val fareInf: Int? = null

    @SerializedName("taxAdt")
    @Expose
    val taxAdt: Int? = null

    @SerializedName("taxChd")
    @Expose
    val taxChd: Int? = null

    @SerializedName("taxInf")
    @Expose
    val taxInf: Int? = null

    @SerializedName("feeAdt")
    @Expose
    val feeAdt: Int? = null

    @SerializedName("feeChd")
    @Expose
    val feeChd: Int? = null

    @SerializedName("feeInf")
    @Expose
    val feeInf: Int? = null

    @SerializedName("serviceFeeAdt")
    @Expose
    val serviceFeeAdt: Int? = null

    @SerializedName("serviceFeeChd")
    @Expose
    val serviceFeeChd: Int? = null

    @SerializedName("serviceFeeInf")
    @Expose
    val serviceFeeInf: Int? = null

    @SerializedName("totalNetPrice")
    @Expose
    val totalNetPrice: Int? = null

    @SerializedName("totalServiceFee")
    @Expose
    val totalServiceFee: Int? = null

    @SerializedName("totalPrice")
    @Expose
    val totalPrice: Int? = null

    @SerializedName("listFlight")
    @Expose
    val listFlight: List<ListFlight>? = null

    @SerializedName("mapingSeatClass")
    @Expose
    val mapingSeatClass: String? = null

    @SerializedName("mapingGroupClass")
    @Expose
    val mapingGroupClass: String? = null

    @SerializedName("mapingGroupClassEng")
    @Expose
    val mapingGroupClassEng: String? = null

    @SerializedName("extraServiceFees")
    @Expose
    val extraServiceFees: Int? = null

    @SerializedName("comparePrice")
    @Expose
    val comparePrice: Int? = null

    @SerializedName("token")
    @Expose
    val token: String? = null

    @SerializedName("session")
    @Expose
    val session: String? = null

    @SerializedName("flightItem")
    @Expose
    val flightItem: FlightItem? = null

    @SerializedName("timeRange")
    @Expose
    val timeRange: String? = null

    @SerializedName("medianPrice")
    @Expose
    val medianPrice: Int? = null

    @SerializedName("cheapestPrice")
    @Expose
    val cheapestPrice: Int? = null

    @SerializedName("avgPrice")
    @Expose
    val avgPrice: Double? = null
}