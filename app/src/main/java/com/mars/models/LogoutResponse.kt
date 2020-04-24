package com.mars.models

import com.mars.network.APIResponse

class LogoutResponse: APIResponse()  {

    lateinit var Attendance: ArrayList<DefaultStatusModel>
}