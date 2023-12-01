package com.myloginfbrd

import ImageAdapter
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
import android.os.Environment
import android.util.Log
import android.widget.RelativeLayout
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


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
            Log.d("MainActivity", "Displaying saved images")
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
                Log.d("MainActivity", "Displaying saved images")
                displaySavedImages()
            } else {
                // Handle permission denial
                // You may show a message to the user or take appropriate action

                // Check which permissions were denied
                val deniedPermissions = mutableListOf<String>()
                for (i in permissions.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permissions[i])
                    }
                }
            }
        }
    }
    private fun displaySavedImages() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val directory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Images")

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()

            val imageList = mutableListOf<Uri>()
            for (file in files) {
                // Use FileProvider to generate a content URI for the file
                val uri = FileProvider.getUriForFile(this, "com.myloginfbrd.fileprovider", file)
                imageList.add(uri)
            }

            // Create an instance of your custom RecyclerViewAdapter
            val adapter = ImageAdapter(imageList)

            // Use GridLayoutManager with span count 4 for 4 images side by side
            val layoutManager = GridLayoutManager(this, 4)

            // Attach the adapter and layout manager to the RecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
        }
    }

    private fun DeleteDisplayedImages() {

    }




    companion object {
        private const val MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 123
    }
}