package ru.vk_hack.museumguide.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Vitaly Nikonorov on 22.10.17.
 * email@nikonorov.net
 */
data class Author (
        @SerializedName("full_name") val fullName: String,
        @SerializedName("paintings") val paintings: List<Painting>
): Serializable