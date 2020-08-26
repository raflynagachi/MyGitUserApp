package com.dicoding.picodiploma.mygituserapp.alarm.broadcast

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.view.MainActivity
import java.util.*

class AlarmReceiver: BroadcastReceiver() {

    companion object{
        const val TYPE_REPEATER ="repeat"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"

        private const val ID_REPEATER = 190
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra(EXTRA_TYPE)
        val notificationId = if (type.equals(TYPE_REPEATER, ignoreCase = true)) ID_REPEATER else return
        val message = intent?.getStringExtra(EXTRA_MESSAGE) as String
        showAlarmNotification(context, message, notificationId)
    }

    private fun showAlarmNotification(context: Context?, message: String, notificationId: Int) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "DailyAlarm channel"

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationManagerCompat = context?.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.baseline_access_alarm_black_24)
            .setContentTitle(context.getString(R.string.daily_reminder))
            .setContentText(message)
            .setSound(alarmSound)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)

    }

    fun setRepeatingAlarm(context: Context, type: String, message: String){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_TYPE, type)
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATER, intent, 0)
        val different = calendar.timeInMillis - System.currentTimeMillis()
        val time: Long
        if (different > 0) {
            time = calendar.timeInMillis
        } else {
            time = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY - different
        }
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, context.getString(R.string.daily_reminder_activated), Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, type: String){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE_REPEATER, ignoreCase = true)) ID_REPEATER else return
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, context.getString(R.string.daily_reminder_canceled), Toast.LENGTH_SHORT).show()
    }
}