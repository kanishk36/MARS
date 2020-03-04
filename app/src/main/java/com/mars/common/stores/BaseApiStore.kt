package com.mars.common.stores

import com.mars.common.listeners.APIResponseListener
import com.mars.network.APIResponse
import com.mars.network.SystemErrorHandler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseApiStore {

    protected fun <T> interactionResultHandler(response: Observable<T>, clazz: Class<out APIResponse>): Observable<T> {
        return response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(
            SystemErrorHandler(clazz)
        )
    }

    protected fun evaluateServiceResponse(apiResponse: APIResponse, listener: APIResponseListener) {
        if (apiResponse.result!!.isSuccess) {
            listener.onSuccess(apiResponse)
        } else {
            listener.onError(apiResponse.result!!.errorInfo)
        }
    }
}