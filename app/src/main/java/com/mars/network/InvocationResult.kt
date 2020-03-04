package com.mars.network

import com.mars.utils.AppConstants


class InvocationResult {

    var status: String? = null
    var errorInfo: ErrorInfo? = null


    val isSuccess: Boolean
        get() = AppConstants.SUCCESS.equals(status!!, ignoreCase = true)

    fun setSuccess() {
        status = AppConstants.SUCCESS
    }

    fun setFailure(code: String?, desc: String?) {
        status = AppConstants.ERROR
        errorInfo = ErrorInfo(code, desc)
    }

}
