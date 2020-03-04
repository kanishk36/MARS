package com.mars.viewmodels

import androidx.lifecycle.MutableLiveData
import com.mars.common.base.BaseViewModel
import com.mars.common.stores.LoginApiStore
import com.mars.models.LoginResponse
import com.mars.network.APIResponse
import com.mars.utils.AppCache
import com.mars.utils.AppConstants

class LoginViewModel: BaseViewModel() {

    val loginResponse: MutableLiveData<String> = MutableLiveData()

    fun login(userName: String, password: String) {
        LoginApiStore.login(this, this, userName, password)
    }

    override fun onSuccess(apiResponse: APIResponse?) {
        super.onSuccess(apiResponse)
        AppCache.INSTANCE.setUserInfo((apiResponse as LoginResponse).Login[0])
        loginResponse.value = AppConstants.SUCCESS
    }

}