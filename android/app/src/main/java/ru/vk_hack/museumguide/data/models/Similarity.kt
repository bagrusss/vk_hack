package ru.vk_hack.museumguide.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
data class Similarity(
        val url: String,
        @SerializedName("picture_url") val pictureUrl: String
)