package com.myloginfbrd

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class FullscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val imageView: ImageView = findViewById(R.id.fullscreenImageView)

        // Receive the image URI from the intent
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")

        // Load the image into the ImageView using Glide or any other library
        Glide.with(this)
            .load(imageUri)
            .into(imageView)
    }
}