package com.example.fieldbuzz.apiUtil

import com.example.fieldbuzz.model.LoginPayload
import com.example.fieldbuzz.model.LoginResponse
import com.example.fieldbuzz.model.UserInfoPayload
import com.example.fieldbuzz.model.UserInfoResponse
import com.example.fieldbuzz.util.AppConstants
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @POST(AppConstants.LOGIN)
    fun postCredentials(@Body loginPayload: LoginPayload): Call<LoginResponse>

    @POST(AppConstants.INFO_FINAL)
    fun postUserInfo(
            @Header("Authorization") token: String,
            @Body userInfo: UserInfoPayload
    ): Call<UserInfoResponse>

    @Multipart
    @POST("file-object/")
    fun postFile(
            @Header("Authorization") token: String,
            @Part("id") id: Int,
            @Part file: MultipartBody.Part): Call<UserInfoResponse.CvFile>
}