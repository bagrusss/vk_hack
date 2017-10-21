package ru.vk_hack.museumguide.data

import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vk_hack.museumguide.data.entity.RecognizeResponse
import java.io.File

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
class Network {

    val API_BASE_URL = "https://some.ru/"

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    private val api = builder.client(httpClient.build()).build().create(Api::class.java)

    public fun uploadPhoto(path : String) : Observable<RecognizeResponse> {
        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("name", file.getName(), requestFile)
        return api.uploadPhoto("url", body)
    }

}
