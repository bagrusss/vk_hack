package ru.vk_hack.museumguide.data

import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vk_hack.museumguide.data.models.Painting
import ru.vk_hack.museumguide.data.models.RecognizeResponse
import java.io.File

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
object Network {

        private const val API_BASE_URL = "http://92.53.103.58"

        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val httpClient = OkHttpClient.Builder().addInterceptor(logging)

        private val builder = Retrofit.Builder()
                                      .baseUrl(API_BASE_URL)
                                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                      .addConverterFactory(GsonConverterFactory.create())

        private val api = builder.client(httpClient.build())
                                 .build()
                                 .create(Api::class.java)

        @JvmStatic
        fun uploadPhoto(path : String) : Observable<RecognizeResponse> {
            val file = File(path)
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
            val body = MultipartBody.Part.createFormData("file", path, requestFile)
            return api.uploadPhoto(body)
        }

        @JvmStatic
        fun getPaintings() = api.getPaintings()

        @JvmStatic
        fun getEvents() = api.getEvents()
}
