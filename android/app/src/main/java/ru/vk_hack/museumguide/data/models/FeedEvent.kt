package ru.vk_hack.museumguide.data.models

import java.io.Serializable

/**
 * Created by bagrusss on 21.10.17
 */
data class FeedEvent(
        val picture: String,
        val title: String,
        val location: String,
        val description: String,
        val dates: String
): Serializable