package ru.vk_hack.museumguide.presentation.feed

import ru.vk_hack.museumguide.data.models.Event
import ru.vk_hack.museumguide.databinding.EventItemBinding
import ru.vk_hack.museumguide.presentation.list.DataBindingViewHolder

/**
 * Created by bagrusss on 21.10.17
 */
class FeedViewHolder(binding: EventItemBinding): DataBindingViewHolder<EventItemBinding, Event>(binding) {

    private val feedImage = binding.imageSrc
    private val title = binding.titleText

    override fun onBind(data: Event) {
        
    }

}