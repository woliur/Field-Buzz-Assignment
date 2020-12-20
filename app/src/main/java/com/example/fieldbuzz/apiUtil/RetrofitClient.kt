package com.example.fieldbuzz.apiUtil

import com.example.fieldbuzz.BuildConfig
import com.example.fieldbuzz.util.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.System.*
import java.util.concurrent.TimeUnit

class RetrofitClient {
    var client: OkHttpClient? = null
    var retrofit: Retrofit? = null

    fun <S> createService(serviceClass: Class<S>?): S {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
//                .addInterceptor { chain ->
//                    val request = chain.request().newBuilder()
//                            .addHeader("User-Agent", getProperty("http.agent"))
//                            .addHeader("portal-name", "manager-app").build()
//                    chain.proceed(request)
//                }
//        if (BuildConfig.DEBUG) {
//            try {
//                builder.addInterceptor(interceptor)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
        client = builder.build()
        retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(client!!)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit!!.create(serviceClass)
    }
}