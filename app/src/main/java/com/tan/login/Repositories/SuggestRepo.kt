package com.tan.login.Repositories

import android.util.Log
import com.tan.login.API.ISuggestApi
import com.tan.login.API.RetrofitHelper
import com.tan.login.Models.Suggest.DataSuggest
import retrofit2.Call
import retrofit2.Response

class SuggestRepo {

    private val suggestApi: ISuggestApi = RetrofitHelper.getInstance().create(ISuggestApi::class.java)

    suspend fun getSuggest(suggest: String): Response<DataSuggest> {
        return suggestApi.getSuggest(keyword = suggest)
    }
}