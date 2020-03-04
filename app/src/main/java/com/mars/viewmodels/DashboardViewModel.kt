package com.mars.viewmodels

import androidx.lifecycle.MutableLiveData
import com.mars.common.base.BaseViewModel
import com.mars.common.stores.DashboardApiStore
import com.mars.models.AttendanceModel
import com.mars.models.dashboard.MarkAttendanceResponse
import com.mars.models.dashboard.ViewAttendanceResponse
import com.mars.network.APIResponse

class DashboardViewModel: BaseViewModel() {

    val markAttendanceResponse: MutableLiveData<String> = MutableLiveData()
    val viewAttendanceResponse: MutableLiveData<ArrayList<AttendanceModel>> = MutableLiveData()

    fun markAttendance(id: String, location: String, attendance: String) {
        DashboardApiStore.markAttendance(this, this, id, location, attendance)
    }

    fun viewAttendance(id: String) {
        DashboardApiStore.viewAttendance(this, this, id)
    }

    override fun onSuccess(apiResponse: APIResponse?) {
        super.onSuccess(apiResponse)
        if(apiResponse is MarkAttendanceResponse) {
            markAttendanceResponse.value = apiResponse.Attendance[0].message

        } else if(apiResponse is ViewAttendanceResponse) {
            viewAttendanceResponse.value = apiResponse.ViewAtten
        }
    }
}