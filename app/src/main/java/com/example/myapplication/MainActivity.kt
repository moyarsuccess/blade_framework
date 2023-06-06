package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.customWidget.init(this)
        binding.btnClose.setOnClickListener { finish() }
        binding.btnOpenPlayer.setOnClickListener {
            startActivity(PlayerActivity.getIntent(this, binding.customWidget.scopeComponentId))
        }
    }
}