package com.example.fieldbuzz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fieldbuzz.apiUtil.Resource
import com.example.fieldbuzz.model.LoginPayload
import com.example.fieldbuzz.model.LoginResponse
import com.example.fieldbuzz.repository.LogInRepository

class LogInViewModel: ViewModel() {
    private val _credentials = MediatorLiveData<Resource<LoginResponse>>()
    val credentials: LiveData<Resource<LoginResponse>> = _credentials

    private val _eventNextFragment = MutableLiveData<Boolean>()
    val eventNextFragment: LiveData<Boolean> get() = _eventNextFragment

    fun postCredintial(loginPayload: LoginPayload) {
        _credentials.addSource(LogInRepository.postCredentials(loginPayload)) {
            _credentials.value = it
        }
    }

    fun onNextFragment() {
        _eventNextFragment.value = true
    }

    fun onNextFragmentComplete(){
        _credentials.value = null
    }

}