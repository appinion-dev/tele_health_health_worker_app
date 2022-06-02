package com.aah.sftelehealthworker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aah.sftelehealthworker.models.login.HealthWorker

@Dao
interface IDaoAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHealthWorker(healthWorker: HealthWorker)

//    @Query("SELECT * FROM HealthWorker")
//    fun getHealthWorker(): LiveData<HealthWorker>

    @Query("SELECT * FROM HealthWorker")
    fun getHealthWorker(): List<HealthWorker>

    @Query("SELECT token FROM HealthWorker")
    fun getToken():String

    @Query("DELETE FROM HealthWorker")
    fun deleteHealthWorker()

    @Query("SELECT branch_id FROM HealthWorker")
    fun getBranchId():Int

//    @Query(
//        "SELECT * FROM User WHERE " +
//                "addr_home_lat BETWEEN :lat1 AND :lat2" +
//                " AND addr_home_lng BETWEEN :lng1 AND :lng2"
//    )
//    fun findInRange(lat1: Long, lat2: Long, lng1: Long, lng2: Long): List<User?>?
}