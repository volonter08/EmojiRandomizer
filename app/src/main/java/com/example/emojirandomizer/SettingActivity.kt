package com.example.emojirandomizer

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private val SWITCH = "switch"
    lateinit var switch:SwitchMaterial
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initviews_setting()
    }
    fun initviews_setting(){
        switch = findViewById(R.id.button_switch)
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
    }

    override fun onPause() {
        super.onPause()
        val editor = prefs.edit()
        editor.putBoolean(SWITCH, switch.isChecked).apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,MainActivity::class.java).apply{
            putExtra("switch",switch.isChecked)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (prefs.contains(SWITCH)) {
            switch.isChecked = prefs.getBoolean(SWITCH,true)
        }
    }
}