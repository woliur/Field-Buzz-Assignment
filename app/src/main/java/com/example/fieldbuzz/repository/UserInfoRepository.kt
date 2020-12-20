package com.example.fieldbuzz.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fieldbuzz.apiUtil.EndPoints
import com.example.fieldbuzz.apiUtil.Resource
import com.example.fieldbuzz.apiUtil.RetrofitClient
import com.example.fieldbuzz.model.UserInfoPayload
import com.example.fieldbuzz.model.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserInfoRepository {

    private val apiClient = RetrofitClient().createService(EndPoints::class.java)

    fun postInfo(token: String, userInfoPayload: UserInfoPayload): LiveData<Resource<UserInfoResponse>> {

        val data: MutableLiveData<Resource<UserInfoResponse>> = MutableLiveData()
        data.value = Resource.loading()

        apiClient.postUserInfo("Token ${token}", userInfoPayload).enqueue(object : Callback<UserInfoResponse>{
            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                if (response.isSuccessful){
                    data.value = Resource.success(response.body())
                }else {
                    data.value = Resource.error(response.errorBody()?.string() ?: "failed in onResponse")
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                data.value = Resource.error(t.localizedMessage ?: "failed in onFailure")
            }
        })


        return data
    }

    fun postFile(token: String, id: Int, file: MultipartBody.Part): LiveData<Resource<UserInfoResponse.CvFile>> {

        val data: MutableLiveData<Resource<UserInfoResponse.CvFile>> = MutableLiveData()
        data.value = Resource.loading()

        apiClient.postFile("Token ${token}", id, file).enqueue(object : Callback<UserInfoResponse.CvFile>{
            override fun onResponse(call: Call<UserInfoResponse.CvFile>, response: Response<UserInfoResponse.CvFile>) {
                if (response.isSuccessful){
                    data.value = Resource.success(response.body())
                }else {
                    data.value = Resource.error(response.errorBody()?.string() ?: "failed in onResponse")
                }
            }

            override fun onFailure(call: Call<UserInfoResponse.CvFile>, t: Throwable) {
                data.value = Resource.error(t.localizedMessage ?: "failed in onFailure")
            }
        })

        return data
    }
}