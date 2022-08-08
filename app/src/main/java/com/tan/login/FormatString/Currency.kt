package com.tan.login.FormatString

import java.text.DecimalFormat
import java.text.NumberFormat

class Currency {

    companion object {
        fun displayVndFormat(money: Int): String {
            val formatter: NumberFormat = DecimalFormat("#,###")
            return formatter.format(money)+"đ"
        }
    }
}