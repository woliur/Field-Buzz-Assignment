package com.example.fieldbuzz.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fieldbuzz.apiUtil.EndPoints
import com.example.fieldbuzz.apiUtil.Resource
import com.example.fieldbuzz.apiUtil.RetrofitClient
import com.example.fieldbuzz.model.LoginPayload
import com.example.fieldbuzz.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LogInRepository {
    private val apiClient = RetrofitClient().createService(EndPoints::class.java)

    fun postCredentials(loginPayload: LoginPayload): LiveData<Resource<LoginResponse>>{

        val data: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
        data.value = Resource.loading()

        apiClient.postCredentials(loginPayload).enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    if (response.code() == 200){
                        data.value = Resource.success(response.body())
                    } else {
                        data.value = Resource.error(response.message())
                    }
                }else {
                    data.value = Resource.error(response.errorBody()?.string() ?: "failed in onResponse")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                data.value = Resource.error(t.localizedMessage ?: "failed in onFailure")
            }
        })


        return data
    }

}