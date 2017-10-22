package ru.vk_hack.museumguide.data

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import ru.vk_hack.museumguide.data.models.EventsResponse
import ru.vk_hack.museumguide.data.models.FeedEvent
import ru.vk_hack.museumguide.data.models.Painting
import ru.vk_hack.museumguide.data.models.RecognizeResponse

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
interface Api {

    @Multipart
    @POST("/recognition/recognize")
    fun uploadPhoto(@Part file: MultipartBody.Part): Observable<RecognizeResponse>

    @GET("/painting")
    fun getPaintings(): Observable<List<Painting>>

    @GET("/recognition/program")
    fun getEvents(): Observable<EventsResponse>

}
