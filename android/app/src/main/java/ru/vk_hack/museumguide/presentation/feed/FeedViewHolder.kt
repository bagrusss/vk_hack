package ru.vk_hack.museumguide.presentation.feed

import com.bumptech.glide.Glide
import ru.vk_hack.museumguide.data.models.FeedEvent
import ru.vk_hack.museumguide.databinding.EventItemBinding
import ru.vk_hack.museumguide.presentation.details.EventActivity
import ru.vk_hack.museumguide.presentation.list.DataBindingViewHolder
import ru.vk_hack.museumguide.utils.ImageUtils

/**
 * Created by bagrusss on 21.10.17
 */
class FeedViewHolder(binding: EventItemBinding): DataBindingViewHolder<EventItemBinding, FeedEvent>(binding),
                                                 ItemListener {

    private val bindingData = FeedData()

    init {
        binding.data = bindingData
        binding.listener = this
    }

    override fun onBind(data: FeedEvent) {
        binding.feedEvent = data

        ImageUtils.loadImage(binding.imageSrc, data.picture)

        bindingData.title.set(data.title)
    }

    override fun onFeedItemClicked(event: FeedEvent) {
        EventActivity.start(binding.root.context, event)
    }

}