package com.mars.network

import com.mars.utils.AppConstants
import io.reactivex.functions.Function


class SystemErrorHandler<T>(private val clazz: Class<out APIResponse>) : Function<Throwable, T> {

    override fun apply(exception: Throwable): T {
        val errorInfo = ErrorInfo()
        if (null == exception) {
            errorInfo.code = AppConstants.SYS_DEFAULT_ERROR
        } else {
            try {
                val retrofitException = exception as RetrofitException?
                if (RetrofitException.Kind.HTTP == retrofitException!!.kind) {
                    errorInfo.code = AppConstants.SYS_HTTP_ERROR

                } else if (RetrofitException.Kind.API_TIMEOUT == retrofitException.kind) {
                    errorInfo.code = AppConstants.API_TIMEOUT_ERROR

                } else if (RetrofitException.Kind.INTERNET == retrofitException.kind) {
                    errorInfo.code = AppConstants.INTERNET_ERROR

                } else if (RetrofitException.Kind.NETWORK == retrofitException.kind) {
                    errorInfo.code = AppConstants.SYS_IO_ERROR

                } else if (RetrofitException.Kind.UNEXPECTED == retrofitException.kind) {
                    errorInfo.code = AppConstants.SYS_DEFAULT_ERROR

                } else if (RetrofitException.Kind.UNKNOWN_HOST == retrofitException.kind) {
                    errorInfo.code = AppConstants.INTERNET_ERROR
                } else {
                    errorInfo.code = AppConstants.SYS_DEFAULT_ERROR
                }
            } catch (ex2: Exception) {
                errorInfo.code = AppConstants.SYS_DEFAULT_ERROR
            }

        }

        //sourceActivity.showError(errorInfo);

        val obj = clazz.newInstance()
        obj.setError(errorInfo.code, errorInfo.desc)
        @Suppress("UNCHECKED_CAST")
        return obj as T
    }
}
