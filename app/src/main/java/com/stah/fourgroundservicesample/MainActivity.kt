package com.stah.fourgroundservicesample

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

import android.app.Notification
import android.app.PendingIntent
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        buttonService.setOnClickListener {
            println("button on")





            val serviceIntent = Intent(this, ForegroundService::class.java)
            startForegroundService(serviceIntent)

        }

    }
}
