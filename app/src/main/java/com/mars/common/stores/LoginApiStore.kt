package com.mars.common.stores

import com.mars.common.listeners.APIResponseListener
import com.mars.common.listeners.ProgressIndicator
import com.mars.models.LoginResponse
import com.mars.network.ServiceInvoker
import com.mars.utils.AppConstants

object LoginApiStore: BaseApiStore() {

    fun login(listener: APIResponseListener, progress: ProgressIndicator, userName: String, password: String) {

        progress.onStartProgress()

        val url = AppConstants.ServiceURLs.LOGIN_URL.format(userName, password)
        val observable = ServiceInvoker.Instance.invoke<LoginResponse>(
            url, LoginResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, LoginResponse::class.java)
            .subscribe { response ->
                if(response.Login != null && !response.Login.isEmpty()) {
                    val status = response.Login[0].Status
                    if(AppConstants.SUCCESS.equals(status, ignoreCase = true)) {
                        response.setSuccess()

                    } else {
                        response.setError(AppConstants.ERROR, response.Login[0].message)
                    }
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }
}