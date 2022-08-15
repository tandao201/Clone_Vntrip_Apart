package com.tan.login.Models.Suggest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Ancestor {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

}
