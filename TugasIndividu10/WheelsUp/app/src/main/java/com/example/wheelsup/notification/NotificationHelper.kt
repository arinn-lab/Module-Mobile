package com.example.wheelsup.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wheelsup.R

object NotificationHelper {

    private const val CHANNEL_ID = "service_reminder_channel"
    private const val CHANNEL_NAME = "Pengingat Servis"
    private const val CHANNEL_DESCRIPTION = "Notifikasi saat motor mendekati jadwal servis berikutnya"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    fun showServiceReminder(
        context: Context,
        motorId: Int,
        motorNickname: String,
        lastOdometer: Int,
        serviceIntervalKm: Int
    ) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Saatnya servis $motorNickname")
            .setContentText(
                "Odometer saat ini ${lastOdometer}km, mendekati interval servis ${serviceIntervalKm}km. Yuk jadwalkan servis."
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(motorId, notification)
    }
}