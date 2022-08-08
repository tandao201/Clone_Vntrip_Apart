package com.tan.login.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tan.login.Dao.IUserDao
import com.tan.login.Models.Location
import com.tan.login.Models.Login.DataLogin

@Database(entities = [DataLogin::class], version = 1)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): IUserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDB? = null

        fun getDatabase(context: Context?): UserDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context?): UserDB {
            return Room.databaseBuilder(
                context!!.applicationContext,
                UserDB::class.java,
                "user_database"
            ).build()
        }
    }
}