package com.mars.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mars.common.listeners.APIResponseListener
import com.mars.common.listeners.ProgressIndicator
import com.mars.common.stores.LoginApiStore
import com.mars.network.APIResponse
import com.mars.network.ErrorInfo
import com.mars.utils.AppCache

abstract class BaseViewModel: ViewModel(), APIResponseListener, ProgressIndicator {

    private val showLoading: MutableLiveData<String> = MutableLiveData()
    private val dismissLoading: MutableLiveData<String> = MutableLiveData()

    private val successResponse: MutableLiveData<APIResponse> by lazy {
        MutableLiveData< APIResponse >()
    }

    private val errorResponse: MutableLiveData<ErrorInfo> by lazy {
        MutableLiveData< ErrorInfo >()
    }

    override fun onStartProgress() {
        showLoading.postValue("")
    }

    override fun onUpdateProgress(percentage: Int) {
    }

    override fun onEndProgress() {
        dismissLoading.postValue("")
    }

    fun errorResponse(): MutableLiveData<ErrorInfo>{
        return errorResponse
    }

    fun successResponse(): MutableLiveData<APIResponse>{
        return successResponse
    }

    fun showLoading(): MutableLiveData<String>{
        return showLoading
    }

    fun dismissLoading(): MutableLiveData<String>{
        return dismissLoading
    }


    override fun onSuccess(apiResponse: APIResponse?) {
        successResponse.value = apiResponse
    }

    override fun onError(errorInfo: ErrorInfo?) {
        errorResponse.value = errorInfo
    }

    fun markoutAttendance(id: String) {
        LoginApiStore.logOut(this, id)
        AppCache.INSTANCE.setUserInfo(null)
    }
}