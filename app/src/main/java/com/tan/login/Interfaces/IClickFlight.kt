package com.tan.login.Interfaces

import com.tan.login.Models.FlightPlace.RegionDatum
import com.tan.login.Models.FlightPlace.SelectPeople

interface IClickFlight {
    fun clickBtnYesSelectPeople(selectPeople: SelectPeople)

    fun clickItemPlaceSearchFlight(regionDatum: RegionDatum)
}