package com.tan.login.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "location")
data class Location (
    var name: String,
    var regionId: Int)  : Serializable  {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}