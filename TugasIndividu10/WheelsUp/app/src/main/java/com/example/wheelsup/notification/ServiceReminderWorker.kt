package com.example.wheelsup.notification

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.wheelsup.data.local.database.WheelsUpDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first

class ServiceReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val reminderThresholdKm = 500

    override suspend fun doWork(): Result {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
            ?: return Result.success()

        val database = Room.databaseBuilder(
            applicationContext,
            WheelsUpDatabase::class.java,
            "wheelsup_database"
        ).build()

        return try {
            val motors = database.motorDao().getAllMotors(userId).first()

            NotificationHelper.createNotificationChannel(applicationContext)

            motors.forEach { motorEntity ->
                val kmSinceLastService = motorEntity.lastOdometer % motorEntity.serviceIntervalKm
                val kmRemaining = motorEntity.serviceIntervalKm - kmSinceLastService

                if (kmRemaining <= reminderThresholdKm) {
                    NotificationHelper.showServiceReminder(
                        context = applicationContext,
                        motorId = motorEntity.id,
                        motorNickname = motorEntity.nickname,
                        lastOdometer = motorEntity.lastOdometer,
                        serviceIntervalKm = motorEntity.serviceIntervalKm
                    )
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        } finally {
            database.close()
        }
    }
}