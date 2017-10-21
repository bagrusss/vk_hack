package ru.vk_hack.museumguide.presentation.feed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import ru.vk_hack.museumguide.R


class FeedActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.recognize_btn -> recognize()
        }
    }

    private lateinit var recognizeBtn : View

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CAMERA_REQUEST -> {

            }
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 1
    }
}
