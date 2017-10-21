package ru.vk_hack.museumguide.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
data class RecognizeResponse(
        @SerializedName("id") val id: String,
        @SerializedName("author") val author: Author,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String,
        @SerializedName("years") val years: String,
        @SerializedName("description") val description: String)
