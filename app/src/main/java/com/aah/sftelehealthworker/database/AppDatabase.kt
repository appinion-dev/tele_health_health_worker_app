package com.aah.sftelehealthworker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aah.sftelehealthworker.models.login.Branch
import com.aah.sftelehealthworker.models.login.HealthWorker
import com.aah.sftelehealthworker.utils.DATABASE_NAME

@Database(
    entities = [HealthWorker::class, Branch::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoAccess(): IDaoAccess

    companion object {
        private var instance: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            return instance?: synchronized(this){
                instance?:buildDatabase(context).also{ instance = it}
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}