package com.mars.network

abstract class APIResponse {

    var result: InvocationResult? = null

    init {
        result = InvocationResult()
    }


    fun setSuccess() {
        result!!.setSuccess()
    }


//    fun setError(errorCode: ErrorCodes) {
//        this.result!!.setFailure(errorCode.code, errorCode.desc)
//    }

    fun setError(code: String?, desc: String?) {
        this.result!!.setFailure(code, desc)

    }

}
