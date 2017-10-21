package ru.vk_hack.museumguide.data

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import ru.vk_hack.museumguide.data.models.Painting
import ru.vk_hack.museumguide.data.models.RecognizeResponse

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
interface Api {

    @Multipart
    @POST
    fun uploadPhoto(@Url url: String,
                         @Part file: MultipartBody.Part): Observable<RecognizeResponse>

    @GET("/painting")
    fun getPaintings(): Observable<List<Painting>>

}
