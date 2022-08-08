package com.tan.login.Repositories

import com.tan.login.API.IHotelSearchApi
import com.tan.login.API.RetrofitHelper
import com.tan.login.Models.HotelSearch.HotelSearch
import com.tan.login.Models.HotelSearch.ResponseHotelSearch
import retrofit2.Response

class HotelSearchRepo {

    private val iHotelSearchApi: IHotelSearchApi = RetrofitHelper.getInstance().create(IHotelSearchApi::class.java)

    suspend fun getHotelSearch(request_source :String, province_id: Int, nights: Int, page: Int, check_in_date:String) : Response<ResponseHotelSearch> {
        return  iHotelSearchApi.getHotelSearch(request_source, province_id, nights, page, check_in_date )
    }
}