package com.example.wheelsup.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wheelsup.data.local.dao.MotorDao
import com.example.wheelsup.data.local.dao.ServiceLogDao
import com.example.wheelsup.data.local.entity.MotorEntity
import com.example.wheelsup.data.local.entity.ServiceLogEntity

@Database(
    entities = [MotorEntity::class, ServiceLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WheelsUpDatabase : RoomDatabase() {
    abstract fun motorDao(): MotorDao
    abstract fun serviceLogDao(): ServiceLogDao
}