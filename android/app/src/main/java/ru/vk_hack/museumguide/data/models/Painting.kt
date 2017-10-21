package ru.vk_hack.museumguide.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */

data class Painting(
        @SerializedName("id") val id: String,
        @SerializedName("author") val author: String,
        @SerializedName("title") val title: String,
        @SerializedName("image") val pictureUrl: String,
        @SerializedName("years") val years: String,
        @SerializedName("description") val description: String
)