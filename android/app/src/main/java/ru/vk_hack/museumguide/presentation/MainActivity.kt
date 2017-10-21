package ru.vk_hack.museumguide.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import ru.vk_hack.museumguide.R


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.recognize_btn -> recognize()
        }
    }

    private lateinit var recognizeBtn : View
    private val CAMERA_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recognizeBtn = findViewById(R.id.recognize_btn)
        recognizeBtn.setOnClickListener(this)
    }

    private fun recognize() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }
}
