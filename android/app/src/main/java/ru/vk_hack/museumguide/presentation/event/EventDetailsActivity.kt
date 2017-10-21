package ru.vk_hack.museumguide.presentation.event

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ru.vk_hack.museumguide.R
import ru.vk_hack.museumguide.data.models.FeedEvent
import ru.vk_hack.museumguide.databinding.ActivityEventDetailsBinding
import ru.vk_hack.museumguide.utils.ImageUtils

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailsBinding

    private val data = EventData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_details)
        binding.data = data

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getSerializableExtra(KEY_DATA)?.let {
            fillData(it as FeedEvent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillData(event: FeedEvent) {
        data.dates.set(event.dates)
        data.description.set(event.description)
        data.location.set(event.location)
        data.title.set(event.title)

        ImageUtils.loadImage(binding.eventImage, event.picture)

        title = event.title
    }

    companion object {

        private const val KEY_DATA = "data"

        @JvmStatic
        fun start(context: Context, event: FeedEvent) {
            context.startActivity(Intent(context, EventDetailsActivity::class.java)
                                        .apply { putExtra(KEY_DATA, event) })
        }
    }
}
