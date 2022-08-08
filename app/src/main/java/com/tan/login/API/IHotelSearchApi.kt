package com.tan.login.API

import com.tan.login.Models.HotelSearch.HotelSearch
import com.tan.login.Models.HotelSearch.ResponseHotelSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IHotelSearchApi {

    @GET("search-engine/search/vntrip-hotel-availability")
    suspend fun getHotelSearch(
        @Query("request_source") request_source: String,
        @Query("province_id") province_id: Int,
        @Query("nights") nights: Int,
        @Query("page") page: Int,
        @Query("check_in_date") check_in_date: String
    ) : Response<ResponseHotelSearch>
}