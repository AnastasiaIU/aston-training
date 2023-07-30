package com.example.astontraining.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.astontraining.databinding.ActivityMainBinding

/**
 * This activity hosts all fragments of the application.
 */
class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}