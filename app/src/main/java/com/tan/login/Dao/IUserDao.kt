package com.tan.login.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tan.login.Models.Login.DataLogin

@Dao
interface IUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDataLogin(dataLogin: DataLogin?)

    @Query("SELECT * FROM data_login ORDER BY ID DESC LIMIT 1")
    suspend fun getTopData(): DataLogin?
}