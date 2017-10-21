package ru.vk_hack.museumguide.presentation.event

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_event_details.*
import ru.vk_hack.museumguide.R
import ru.vk_hack.museumguide.data.models.FeedEvent

class EventDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {

        private const val KEY_DATA = "data"

        @JvmStatic
        fun start(context: Context, event: FeedEvent) {

        }
    }
}
