package com.stah.fourgroundservicesample

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class ForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        println("service oncreate")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("service onStartCommand")


        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, "test")
            .setContentTitle("Foreground Service")
            .setContentText("Foreground Service Example in Android")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)


        Thread(
            Runnable {
                (0..5).map {
                    Thread.sleep(1000)
                    println("foreground service $it")
                }
                stopForeground(true)
                stopSelf()

            }).start()


        return START_STICKY

    }

    override fun onDestroy() {
        println("service done")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "test",
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}