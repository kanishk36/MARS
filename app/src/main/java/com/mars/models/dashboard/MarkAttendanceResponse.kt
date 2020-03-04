package com.mars.models.dashboard

import com.mars.models.DefaultStatusModel
import com.mars.network.APIResponse

class MarkAttendanceResponse: APIResponse()  {

    lateinit var Attendance: ArrayList<DefaultStatusModel>
}