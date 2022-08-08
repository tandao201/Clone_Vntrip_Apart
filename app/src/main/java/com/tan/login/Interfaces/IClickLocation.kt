package com.tan.login.Interfaces

import com.tan.login.Models.Location

interface IClickLocation {

    fun clickedLocationItem(location: Location)

    fun clickedHotelItem(location: Location)
}