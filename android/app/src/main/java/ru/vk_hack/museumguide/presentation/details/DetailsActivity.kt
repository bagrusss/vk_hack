package ru.vk_hack.museumguide.presentation.details

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import ru.vk_hack.museumguide.R
import ru.vk_hack.museumguide.data.models.FeedEvent
import ru.vk_hack.museumguide.databinding.ActivityDetailsBinding
import ru.vk_hack.museumguide.utils.ImageUtils

class DetailsActivity : AppCompatActivity(), DetailsListener {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    private val imagesAdapter = DetailsAdapter()

    private val detailsData = DetailsData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.data = detailsData

        recyclerView = binding.include.authorMoreList
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = imagesAdapter

        toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", {

                    })
                    .show()
        }*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getSerializableExtra(KEY_DATA)?.let {
            fillDataEvent(it as FeedEvent)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.listener = this
    }

    override fun onStop() {
        binding.listener = null
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAudioClicked() {

    }

    override fun onFabClicked() {

    }

    private fun fillDataEvent(event: FeedEvent) {
        title = event.title
        ImageUtils.loadImage(binding.image, event.picture)
    }

    companion object {

        private const val KEY_DATA = "data"

        @JvmStatic
        fun start(context: Context, event: FeedEvent? = null) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(KEY_DATA, event)
            context.startActivity(intent)
        }
    }
}
