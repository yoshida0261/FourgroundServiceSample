package com.stah.fourgroundservicesample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat


class ForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        // oncreateで呼べばいい
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, "test")
            .setContentTitle("Foreground Service")
            .setContentText("Foreground Service Example in Android")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        println("service oncreate")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("service onStartCommand")

        // serviceで実行したい処理を書く
        Thread(
            Runnable {
                (0..4).map {
                    Thread.sleep(1000)
                    println("foreground service $it")
                }
                stopForeground(true)
                // stop selftかstopServiceを呼ばないとサービスは生きる続けてしまう(onDestroyが呼ばれない)
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