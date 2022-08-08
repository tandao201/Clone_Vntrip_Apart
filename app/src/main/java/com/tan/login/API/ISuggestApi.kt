package com.tan.login.API

import com.tan.login.Models.Suggest.DataSuggest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ISuggestApi {

    @GET("/search-engine/search/v2/suggestion")
    suspend fun getSuggest(@Query("keyword") keyword: String) : Response<DataSuggest>
}