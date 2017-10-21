package ru.vk_hack.museumguide.presentation.feed

import com.bumptech.glide.Glide
import ru.vk_hack.museumguide.data.models.Event
import ru.vk_hack.museumguide.databinding.EventItemBinding
import ru.vk_hack.museumguide.presentation.list.DataBindingViewHolder

/**
 * Created by bagrusss on 21.10.17
 */
class FeedViewHolder(binding: EventItemBinding): DataBindingViewHolder<EventItemBinding, Event>(binding) {

    private val bindingData = FeedData()

    init {
        binding.data = bindingData
    }

    override fun onBind(data: Event) {
        Glide.with(binding.root.context)
             .load(data.picture)
             .centerCrop()
             .into(binding.imageSrc)

        bindingData.title.set(data.title)
    }

}