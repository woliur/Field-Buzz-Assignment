package com.example.fieldbuzz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.fieldbuzz.apiUtil.Resource
import com.example.fieldbuzz.model.UserInfoPayload
import com.example.fieldbuzz.model.UserInfoResponse
import com.example.fieldbuzz.repository.UserInfoRepository
import okhttp3.MultipartBody

class UserInfoViewModel: ViewModel() {

    private val _userInfo = MediatorLiveData<Resource<UserInfoResponse>>()
    val userInfo: LiveData<Resource<UserInfoResponse>> = _userInfo

    private val _fileupload = MediatorLiveData<Resource<UserInfoResponse.CvFile>>()
    val fileupload: LiveData<Resource<UserInfoResponse.CvFile>> = _fileupload

    fun postInfo(token: String, userInfoPayload: UserInfoPayload) {
        _userInfo.addSource(UserInfoRepository.postInfo(token ,userInfoPayload)) {
            _userInfo.value = it
        }
    }

    fun postFile(token: String, id: Int, file: MultipartBody.Part) {
        _fileupload.addSource(UserInfoRepository.postFile(token, id ,file)) {
            _fileupload.value = it
        }
    }
}