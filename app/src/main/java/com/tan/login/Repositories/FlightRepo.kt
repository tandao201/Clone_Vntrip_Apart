package com.tan.login.Repositories

import com.tan.login.API.IFlightApi
import com.tan.login.API.RetrofitHelper
import com.tan.login.Models.FlightPlace.ResponseFlight
import com.tan.login.Models.FlightTicket.Response.ResponseSearchFlight
import com.tan.login.Models.FlightTicket.Resquest.RequestSearchFlight
import retrofit2.Response

class FlightRepo {

    private val iFlightApi: IFlightApi = RetrofitHelper.getInstance().create(
        IFlightApi::class.java)

    suspend fun searchFlight() :Response<ResponseFlight> {
        return iFlightApi.searchFlightPlace()
    }

    suspend fun searchFlightTicket(requestSearchFlight: RequestSearchFlight) : Response<ResponseSearchFlight> {
        return iFlightApi.searchFlightTicket(requestSearchFlight)
    }

}