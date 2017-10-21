package ru.vk_hack.museumguide.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
data class Similarity(
        @SerializedName("url") val url: String,
        @SerializedName("picture_url") val pictureUrl: String
)