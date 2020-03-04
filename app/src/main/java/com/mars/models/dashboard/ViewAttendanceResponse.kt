package com.mars.models.dashboard

import com.mars.models.AttendanceModel
import com.mars.network.APIResponse

class ViewAttendanceResponse: APIResponse() {

    lateinit var ViewAtten: ArrayList<AttendanceModel>
}