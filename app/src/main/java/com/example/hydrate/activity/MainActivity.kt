package com.example.hydrate.activity

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hydrate.R
import com.example.hydrate.ReminderBroadcast
import com.example.hydrate.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import java.util.*
import java.util.Calendar.*

//Bartosz Witkowski EiT 136594 TMiB
//kontakt: bartosz.r.witkowski@student.put.poznan.pl

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var mDatabase : DatabaseReference
    private lateinit var binding: ActivityMainBinding
    var channel = "reminderAboutDrinking"
    var calendar = getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_nutrition_box, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        }


    private fun createNotificationsChannel() {

        val channel: NotificationChannel = NotificationChannel(channel, "Reminder about drinking", NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.description = "reminder about drinking"
        channel.lightColor = 2866173

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences("com.example.hydrate_preferences",Context.MODE_PRIVATE) ?: return
        val flag = (sharedPref.getBoolean("notificationsOnOff", false))
        val mainIntent = Intent(this@MainActivity, ReminderBroadcast::class.java)
        val pendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 0, mainIntent, 0)
        val alarmManager : AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (flag == true) {

            if (calendar.get(HOUR_OF_DAY) > 21 || calendar.get(HOUR_OF_DAY) < 8) {
                Toast.makeText(
                    this,
                    "Notifications are turned off between 22:00 and 8:00",
                    Toast.LENGTH_SHORT
                ).show()
                alarmManager.cancel(pendingIntent)
            }
            else {
                createNotificationsChannel()
                val userTime = (sharedPref.getInt("notificationsIntensitySeekBar", 45))

                Toast.makeText(
                    this,
                    "Notifications are turned on, interval: " + userTime.toString() + "min",
                    Toast.LENGTH_SHORT
                ).show()
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60000*userTime.toLong(), 60000*userTime.toLong(), pendingIntent)
            }

        }
        else {
            Toast.makeText(
                this,
                "Notifications are turned off",
                Toast.LENGTH_SHORT
            ).show()
            alarmManager.cancel(pendingIntent)
        }
    }

}