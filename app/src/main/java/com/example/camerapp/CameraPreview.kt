package com.example.camerapp

import android.content.Context
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.IOException

class CameraPreview(context: Context, private val camera: Camera) : SurfaceView(context), SurfaceHolder.Callback {

    private val holder: SurfaceHolder = getHolder()
    private var currentCameraId = 0
    private var isRecording = false

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera.stopPreview()
        camera.release()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        if (holder.surface == null) {
            return
        }

        try {
            camera.stopPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun switchCamera(cameraId: Int) {
        if (currentCameraId != cameraId && !isRecording) {
            currentCameraId = cameraId
            camera.stopPreview()
            camera.release()
            val newCamera = Camera.open(cameraId)
            try {
                newCamera.setPreviewDisplay(holder)
                newCamera.startPreview()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun startRecording() {
        isRecording = true
    }

    fun stopRecording() {
        isRecording = false
    }

    fun isRecordingVideo(): Boolean {
        return isRecording
    }
}