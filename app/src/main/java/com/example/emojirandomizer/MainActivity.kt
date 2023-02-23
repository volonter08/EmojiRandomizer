package com.example.emojirandomizer

import android.content.Intent
import android.content.SharedPreferences
import android.net.http.HttpResponseCache
import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText



class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun initViews() {
        val button = findViewById<MaterialButton>(R.id.button)
        val textinput = findViewById<TextInputEditText>(R.id.text_input)
        textView = findViewById(R.id.textView)
        prefs = getSharedPreferences("main", MODE_PRIVATE)
        button.setOnClickListener {
            open_setting()
        }
        init_intfilter(textinput)
        textinput.setOnEditorActionListener { v, actionid, event ->
            if (actionid == EditorInfo.IME_ACTION_DONE) {
                val text = textinput.text.toString()
                textView.text = randomchik(text)
            }
            return@setOnEditorActionListener false
        }
    }

    fun randomchik(text: String): String? {
        val random = (1..(text.length / 2)).random()
        val charArray = text.toCharArray()
        val map = HashMap<Int, String>()
        for (i in (1..(text.length / 2))) {
            map.put(i, (charArray[(i - 1) * 2].toString() + charArray[i * 2 - 1].toString()))
        }
        val arguments = intent.extras
        if (arguments != null) {
            if (arguments!!.getBoolean("switch"))
                return map.get(text.length / 2)
            else
                return map.get(random)
        } else
            return map.get(random)
    }

    fun open_setting() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    fun init_intfilter(textinput: TextInputEditText) {
        val filter =
            InputFilter { src, start, end, d, dstart, dend ->
                for (i in start until end) {
                    if (src[i].code <= 1279) {
                        return@InputFilter src.subSequence(start, i)
                    }
                }
                return@InputFilter src.subSequence(start, end)
            }
        textinput.setFilters(arrayOf(filter))
    }

    override fun onPause() {
        super.onPause()
        val editor = prefs.edit()
        editor.putString("main", textView.text.toString()).apply()
    }

    override fun onResume() {
        super.onResume()
        if (prefs.contains("main")) {
            textView.text = prefs.getString("main", "")
        }
    }

}