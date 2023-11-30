package com.myloginfbrd

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myloginfbrd.databinding.ActivityMainBinding
import android.widget.ImageView
import android.net.Uri
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import android.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Load and display images when MainActivity starts
        // Check and request permissions
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE
            )
        } else {
            // Permission granted, display saved images
            displaySavedImages()
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
            finish()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Permissions granted, display saved images
                displaySavedImages()
            } else {
                // Handle permission denial
                // You may show a message to the user or take appropriate action
            }
        }
    }
    private fun displaySavedImages() {
        // Specify the directory where images are stored
        val directory = File(getExternalFilesDir(null), "Images")

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()

            // Display each image in an ImageView or RecyclerView
            for (file in files) {
                val imageView = ImageView(this)
                imageView.setImageURI(Uri.fromFile(file))

                // You can customize the layout parameters as needed
                val layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // Add the ImageView to your layout
                binding.imageContainer.addView(imageView, layoutParams)
            }
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 123
    }
}