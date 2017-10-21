package ru.vk_hack.museumguide.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Vitaly Nikonorov on 22.10.17.
 * email@nikonorov.net
 */
data class Author (
        @SerializedName("full_name") val id: String,
        @SerializedName("paintings") val author: List<Painting>
)