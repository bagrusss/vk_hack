package ru.vk_hack.museumguide.presentation.feed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ru.vk_hack.museumguide.R
import ru.vk_hack.museumguide.databinding.ActivityFeedBinding


class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding

    private lateinit var feedRecycler: RecyclerView

    private val adapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed)
        binding.recognizeBtn.setOnClickListener {
            recognize()
        }
        feedRecycler = binding.feedList
        feedRecycler.layoutManager = LinearLayoutManager(this)
        feedRecycler.adapter = adapter
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
