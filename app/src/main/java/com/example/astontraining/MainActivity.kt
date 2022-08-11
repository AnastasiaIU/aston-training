package com.example.astontraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private const val LOG_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_TAG, "Hello World")
    }
}