package com.example.camerapp

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var camera: Camera
    private lateinit var preview: CameraPreview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkCameraHardware()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
            } else {
                setupCamera()
            }
        } else {
            Toast.makeText(this, "No camera found", Toast.LENGTH_SHORT).show()
        }

        val captureButton = findViewById<Button>(R.id.capture_button)
        captureButton.setOnClickListener { // Handle capture button click here
            Toast.makeText(this, "Capture button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCamera() {
        camera = Camera.open() // Open the default camera
        preview = CameraPreview(this, camera)
        val previewFrame = findViewById<FrameLayout>(R.id.camera_preview)
        previewFrame.addView(preview)
    }

    private fun checkCameraHardware(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupCamera()
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}