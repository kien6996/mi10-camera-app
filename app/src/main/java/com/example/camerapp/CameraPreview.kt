package com.example.camerapp

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.media.Image
import android.media.ImageReader
import android.view.Surface
import android.view.TextureView
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CameraPreview : AppCompatActivity() {
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraDevice: CameraDevice
    private lateinit var cameraCaptureSession: CameraCaptureSession

    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var textureView: TextureView

    private var cameraId: String? = null
    private var lensFacing: Int = CameraMetadata.LENS_FACING_BACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_preview)

        textureView = findViewById(R.id.textureView)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // Enable the camera preview functionality on textureView
        setupCameraPreview()
    }

    private fun setupCameraPreview() {
        try {
            cameraId = chooseCamera()
            if (cameraId != null) {
                cameraManager.openCamera(cameraId!!, object : CameraDevice.StateCallback() {
                    override fun onOpened(@NonNull cameraDevice: CameraDevice) {
                        this@CameraPreview.cameraDevice = cameraDevice
                        createCameraPreviewSession()
                    }

                    override fun onDisconnected(@NonNull cameraDevice: CameraDevice) {
                        cameraDevice.close()
                    }

                    override fun onError(@NonNull cameraDevice: CameraDevice, error: Int) {
                        cameraDevice.close()
                    }
                }, null)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun chooseCamera(): String? {
        // Code to choose camera based on lensFacing
        val cameraIdList = cameraManager.cameraIdList
        for (id in cameraIdList) {
            val characteristics = cameraManager.getCameraCharacteristics(id)
            val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
            if (facing != null && facing == lensFacing) {
                return id
            }
        }
        return null
    }

    private fun createCameraPreviewSession() {
        try {
            val surface = Surface(textureView.surfaceTexture)
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)

            cameraDevice.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(@NonNull session: CameraCaptureSession) {
                    cameraCaptureSession = session
                    try {
                        cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }

                override fun onConfigureFailed(@NonNull session: CameraCaptureSession) {
                }
            }, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun switchLens() {
        lensFacing = if (lensFacing == CameraMetadata.LENS_FACING_BACK) {
            CameraMetadata.LENS_FACING_FRONT
        } else {
            CameraMetadata.LENS_FACING_BACK
        }
        // Close current camera and re-initialize
        cameraDevice.close()
        setupCameraPreview()
    }
}