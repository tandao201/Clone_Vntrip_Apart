package com.tan.login.Repositories

import com.tan.login.Dao.ILocationDao
import com.tan.login.Models.Location

class LocationRepo(private val locationDao: ILocationDao) {

    suspend fun addNewLocation(localtion: Location) {
        return locationDao.insertLocation(localtion)
    }

    suspend fun get5LastLocation() : MutableList<Location> {
        return locationDao.getLocation()
    }
}