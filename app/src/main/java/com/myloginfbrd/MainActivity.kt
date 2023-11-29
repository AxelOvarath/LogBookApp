package com.myloginfbrd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myloginfbrd.databinding.ActivityMainBinding
import android.widget.ImageView
import android.net.Uri

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.button2.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
            finish()
        }
    }
}