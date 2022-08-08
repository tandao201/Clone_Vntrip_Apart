package com.tan.login.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tan.login.Dao.ILocationDao
import com.tan.login.Models.Location

@Database(entities = [Location::class], version = 2)
abstract class LocationDB: RoomDatabase() {
    abstract fun locationDao(): ILocationDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE location ADD COLUMN regionId INTEGER NOT NULL DEFAULT 0")
            }

        }
        @Volatile
        private var INSTANCE: LocationDB? = null

        fun getDatabase(context: Context?): LocationDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context?): LocationDB {
            return Room.databaseBuilder(
                context!!.applicationContext,
                LocationDB::class.java,

                "location_database"
            ).addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}
