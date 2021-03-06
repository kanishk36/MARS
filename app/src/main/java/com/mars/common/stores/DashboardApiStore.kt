package com.mars.common.stores

import com.mars.common.listeners.APIResponseListener
import com.mars.common.listeners.ProgressIndicator
import com.mars.models.dashboard.DailyActivityResponse
import com.mars.models.dashboard.GeoLocationResponse
import com.mars.models.dashboard.MarkAttendanceResponse
import com.mars.models.dashboard.ViewAttendanceResponse
import com.mars.network.ServiceInvoker
import com.mars.utils.AppConstants

object DashboardApiStore: BaseApiStore() {

    fun markAttendance(listener: APIResponseListener, progress: ProgressIndicator,
                       id: String, location: String, attendance: String) {

        progress.onStartProgress()

        val url = AppConstants.ServiceURLs.MARK_ATTENDANCE_URL.format(id, location, attendance)
        val observable = ServiceInvoker.Instance.invoke<MarkAttendanceResponse>(
            url, MarkAttendanceResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, MarkAttendanceResponse::class.java)
            .subscribe { response ->
                if(response.result!!.errorInfo == null) {
                    if(response.Attendance != null && !response.Attendance.isEmpty()) {
                        val status = response.Attendance[0].Status
                        if(AppConstants.SUCCESS.equals(status, ignoreCase = true)) {
                            response.setSuccess()

                        } else {
                            response.setError(AppConstants.ERROR, response.Attendance[0].message)
                        }
                    }
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }

    fun viewAttendance(listener: APIResponseListener, progress: ProgressIndicator, id: String) {

        progress.onStartProgress()

        val url = AppConstants.ServiceURLs.VIEW_ATTENDANCE_URL.format(id)
        val observable = ServiceInvoker.Instance.invoke<ViewAttendanceResponse>(
            url, ViewAttendanceResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, ViewAttendanceResponse::class.java)
            .subscribe { response ->

                if(response.result!!.errorInfo == null) {
                    if(response.ViewAtten != null && !response.ViewAtten.isEmpty()) {
                        response.setSuccess()
                    }
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }

    fun getPlace(listener: APIResponseListener, progress: ProgressIndicator, latLng: String) {

        progress.onStartProgress()

        val url = AppConstants.ServiceURLs.GEOCODING_URL.format(latLng)
        val observable = ServiceInvoker.Instance.getPlace(url, GeoLocationResponse::class.java)

        interactionResultHandler(observable, GeoLocationResponse::class.java)
            .subscribe { response ->
                if(response.result!!.errorInfo == null) {
                    if(AppConstants.OK.equals(response.status, ignoreCase = true)) {
                        response.setSuccess()
                    }
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }

    fun markDailyActivity(listener: APIResponseListener, progress: ProgressIndicator, id: String, dailyActivity: String) {

        progress.onStartProgress()

        val url = AppConstants.ServiceURLs.MARK_ACTIVITY_URL.format(id, dailyActivity)
        val observable = ServiceInvoker.Instance.invoke<DailyActivityResponse>(
            url, DailyActivityResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, DailyActivityResponse::class.java)
            .subscribe { response ->
                if(response.result!!.errorInfo == null) {
                    if(response.Attendance != null && !response.Attendance.isEmpty()) {
                        val status = response.Attendance[0].Status
                        if(AppConstants.SUCCESS.equals(status, ignoreCase = true)) {
                            response.setSuccess()

                        } else {
                            response.setError(AppConstants.ERROR, response.Attendance[0].message)
                        }
                    }

                }
                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }
}