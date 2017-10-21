package ru.vk_hack.museumguide.data

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url
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

}
