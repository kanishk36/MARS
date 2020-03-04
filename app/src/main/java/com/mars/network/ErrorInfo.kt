package com.mars.network

class ErrorInfo {
    var code: String? = null
        get() = if (field == null) "" else field
    var desc: String? = null
        get() = if (field == null) "" else field

    constructor() : super() {

    }

    constructor(code: String?, desc: String?) : super() {
        this.code = code
        this.desc = desc
    }
}
