package ru.vk_hack.museumguide.presentation.feed

import ru.vk_hack.museumguide.data.models.FeedEvent

/**
 * Created by bagrusss on 21.10.17
 */
interface ItemListener {
    fun onFeedItemClicked(event: FeedEvent)
}