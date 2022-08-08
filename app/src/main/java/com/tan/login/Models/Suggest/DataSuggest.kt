package com.tan.login.Models.Suggest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class DataSuggest {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

}