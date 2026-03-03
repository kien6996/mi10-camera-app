// CameraPreview.kt

package com.example.camerapp

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraError
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraOutputConfigured
import android.media.ImageReader
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.TextureView

class CameraPreview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : TextureView(context, attrs), TextureView.SurfaceTextureListener {

    private lateinit var cameraManager: CameraManager
    private lateinit var cameraDevice: CameraDevice
    private lateinit var currentCameraId: String

    init {
        surfaceTextureListener = this
    }

    fun setCameraManager(manager: CameraManager, cameraId: String) {
        cameraManager = manager
        currentCameraId = cameraId
        openCamera() // Open the camera
    }

    private fun openCamera() {
        try {
            cameraManager.openCamera(currentCameraId, object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    cameraDevice = camera
                    // Start the camera preview
                }

                override fun onDisconnected(camera: CameraDevice) {
                    camera.close()
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    Log.e(TAG, "Camera error: $error")
                }

            }, null)
        } catch (e: CameraAccessException) {
            Log.e(TAG, "Camera access exception: ${e.message}")
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        // Start rendering camera preview at this point
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        // Handle size changes if needed
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        cameraDevice.close() // Release the camera
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        // Update the preview
    }

    fun switchCamera(newCameraId: String) {
        currentCameraId = newCameraId
        // Close current camera and open the new one
        closeCamera()
        openCamera()
    }

    private fun closeCamera() {
        // Logic to close the camera
        if (::cameraDevice.isInitialized) {
            cameraDevice.close()
        }
    }

    companion object {
        private const val TAG = "CameraPreview"
    }
}