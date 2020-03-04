package com.mars.common.listeners

import com.mars.network.APIResponse
import com.mars.network.ErrorInfo


interface APIResponseListener {

    fun onSuccess(apiResponse: APIResponse?)
    fun onError(errorInfo: ErrorInfo?)

}
