package com.example.smartwatermeter.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.smartwatermeter.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)


        var text = findViewById<TextView>(R.id.info)
        text.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://wwandw.com"))
            startActivity(i)
        }

    }
}