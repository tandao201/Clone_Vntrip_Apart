package com.tan.login.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tan.login.Models.Location

@Dao
interface ILocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Query("select * from location order by id desc limit 5")
    suspend fun getLocation(): MutableList<Location>
}