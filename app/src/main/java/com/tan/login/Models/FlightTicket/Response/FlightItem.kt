package com.tan.login.Models.FlightTicket.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class FlightItem {

    @SerializedName("flightId")
    @Expose
    val flightId: Int? = null

    @SerializedName("leg")
    @Expose
    val leg: Int? = null

    @SerializedName("hasDownStop")
    @Expose
    val hasDownStop: Boolean? = null

    @SerializedName("noRefund")
    @Expose
    val noRefund: Boolean? = null

    @SerializedName("groupClass")
    @Expose
    val groupClass: String? = null

    @SerializedName("fareClass")
    @Expose
    val fareClass: String? = null

    @SerializedName("promo")
    @Expose
    val promo: Boolean? = null

    @SerializedName("stopNum")
    @Expose
    val stopNum: Int? = null

    @SerializedName("airline")
    @Expose
    val airline: String? = null

    @SerializedName("operating")
    @Expose
    val operating: String? = null

    @SerializedName("startPoint")
    @Expose
    val startPoint: String? = null

    @SerializedName("endPoint")
    @Expose
    val endPoint: String? = null

    @SerializedName("startDate")
    @Expose
    val startDate: String? = null

    @SerializedName("startTimeZoneOffset")
    @Expose
    val startTimeZoneOffset: String? = null

    @SerializedName("endDate")
    @Expose
    val endDate: String? = null

    @SerializedName("endTimeZoneOffset")
    @Expose
    val endTimeZoneOffset: String? = null

    @SerializedName("flightNumber")
    @Expose
    val flightNumber: String? = null

    @SerializedName("duration")
    @Expose
    val duration: Int? = null

    @SerializedName("flightValue")
    @Expose
    val flightValue: String? = null

    @SerializedName("listSegment")
    @Expose
    val listSegment: List<ListSegment>? = null

    @SerializedName("provider")
    @Expose
    val provider: String? = null

    @SerializedName("session")
    @Expose
    val session: String? = null
}