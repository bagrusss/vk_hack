package ru.vk_hack.museumguide.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
data class RecognizeResponse(
        @SerializedName("id") val id: String,
        @SerializedName("author") val author: String,
        @SerializedName("biography") val biography: String,
        @SerializedName("picture_url") val pictureUrl: String,
        @SerializedName("description") val description: String,
        @SerializedName("lecture") val lecture: String,
        @SerializedName("music") val music: String,
        @SerializedName("similarities") val similarities : Array<Similarity>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecognizeResponse

        if (id != other.id) return false
        if (author != other.author) return false
        if (biography != other.biography) return false
        if (pictureUrl != other.pictureUrl) return false
        if (description != other.description) return false
        if (lecture != other.lecture) return false
        if (music != other.music) return false
        if (!Arrays.equals(similarities, other.similarities)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + biography.hashCode()
        result = 31 * result + pictureUrl.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + lecture.hashCode()
        result = 31 * result + music.hashCode()
        result = 31 * result + Arrays.hashCode(similarities)
        return result
    }
}
