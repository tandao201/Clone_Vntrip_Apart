package com.tan.login.API

import com.tan.login.Models.FlightPlace.ResponseFlight
import com.tan.login.Models.FlightTicket.Response.ResponseSearchFlight
import com.tan.login.Models.FlightTicket.Resquest.RequestSearchFlight
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IFlightApi {

    @GET("v1-flight/atadi-air-ports")
    suspend fun searchFlightPlace() : Response<ResponseFlight>

    @POST("flight-service/v1/search")
    suspend fun searchFlightTicket(@Body requestSearchFlight: RequestSearchFlight) : Response<ResponseSearchFlight>
}