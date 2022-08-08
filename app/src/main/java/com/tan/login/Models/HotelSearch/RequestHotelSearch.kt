package com.tan.login.Models.HotelSearch

import java.io.Serializable

data class RequestHotelSearch(
    var request_source: String,
    var province_id: Int,
    var nights: Int,
    var page: Int,
    var check_in_date: String,
    var checkInDateFull: String,
    var checkOutDateFull: String,
    var province_name: String) : Serializable{
}