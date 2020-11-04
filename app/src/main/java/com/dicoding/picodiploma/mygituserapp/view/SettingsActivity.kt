package com.dicoding.picodiploma.mygituserapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.alarm.broadcast.AlarmReceiver
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val PREFS_NAME = "settings"
        private const val REPEAT = "daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (supportActionBar != null){
            supportActionBar?.title = getString(R.string.settings)
        }
        change_language.setOnClickListener(this)
        switchReminder()
    }

    private fun switchReminder() {
        sw_daily.isChecked = mSharedPreferences.getBoolean(REPEAT, false)
        sw_daily.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked){
                alarmReceiver.setRepeatingAlarm(
                    applicationContext,
                    AlarmReceiver.TYPE_REPEATER,
                    getString(R.string.daily_message)
                )
            }else{
                alarmReceiver.cancelAlarm(applicationContext, AlarmReceiver.TYPE_REPEATER)
            }
            saveChange(isChecked)
        }
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(REPEAT, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.change_language ->{
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
    }
}