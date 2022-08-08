package com.tan.login.Models.FlightTicket.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ListSegment {

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("airline")
    @Expose
    val airline: String? = null

    @SerializedName("startPoint")
    @Expose
    val startPoint: String? = null

    @SerializedName("endPoint")
    @Expose
    val endPoint: String? = null

    @SerializedName("startTime")
    @Expose
    val startTime: String? = null

    @SerializedName("startTimeZoneOffset")
    @Expose
    val startTimeZoneOffset: String? = null

    @SerializedName("endTime")
    @Expose
    val endTime: String? = null

    @SerializedName("endTimeZoneOffset")
    @Expose
    val endTimeZoneOffset: String? = null

    @SerializedName("flightNumber")
    @Expose
    val flightNumber: String? = null

    @SerializedName("plane")
    @Expose
    val plane: String? = null

    @SerializedName("duration")
    @Expose
    val duration: Int? = null

    @SerializedName("class")
    @Expose
    val _class: String? = null

    @SerializedName("startTerminal")
    @Expose
    val startTerminal: Any? = null

    @SerializedName("endTerminal")
    @Expose
    val endTerminal: Any? = null

    @SerializedName("hasStop")
    @Expose
    val hasStop: Boolean? = null

    @SerializedName("stopPoint")
    @Expose
    val stopPoint: String? = null

    @SerializedName("stopTime")
    @Expose
    val stopTime: Int? = null

    @SerializedName("dayChange")
    @Expose
    val dayChange: Boolean? = null

    @SerializedName("stopOvernight")
    @Expose
    val stopOvernight: Boolean? = null

    @SerializedName("changeStation")
    @Expose
    val changeStation: Boolean? = null

    @SerializedName("changeAirport")
    @Expose
    val changeAirport: Boolean? = null

    @SerializedName("lastItem")
    @Expose
    val lastItem: Boolean? = null

    @SerializedName("handBaggage")
    @Expose
    val handBaggage: String? = null
}