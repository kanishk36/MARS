package com.mars.utils

class AppConstants {

    companion object {

        const val ERROR = "error"
        const val SUCCESS = "success"

        //error codes
        const val SYS_HTTP_ERROR = "SYS_HTTP_ERROR"
        const val SYS_IO_ERROR = "SYS_IO_ERROR"
        const val API_TIMEOUT_ERROR = "API_TIMEOUT_ERROR"
        const val SYS_DEFAULT_ERROR = "SYS_DEFAULT_ERROR"
        const val INTERNET_ERROR = "INTERNET_ERROR"

    }

    interface ServiceURLs {
        companion object {

            val BASE_URL = "http://fundoplay.com/attendance/api/"
            val LOGIN_URL = "login.php?userid=%s&password=%s"
            val MARK_ATTENDANCE_URL = "markatte.php?id=%s&alocation=%s&aattendance=%s"
            val VIEW_ATTENDANCE_URL = "viewatten.php?id=%s"
            val FAQ_URL = "http://marscltc.com/faq.php"
            val USER_GUIDE_URL = "http://marscltc.com/userguide.php"
        }
    }

    interface HttpMethods {
        companion object {
            val HTTP_GET = "GET"
        }
    }

}