package com.mars.ui.dashboard


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mars.R
import com.mars.common.base.AbstractFragment
import com.mars.models.UserInfo
import com.mars.network.ErrorInfo
import com.mars.utils.AppCache
import com.mars.utils.LocationUpdateManager
import com.mars.utils.LocationUpdateManager.ACCESS_COARSE_LOCATION_PERMISSION
import com.mars.utils.LocationUpdateManager.ACCESS_FINE_LOCATION_PERMISSION
import com.mars.viewmodels.DashboardViewModel

/**
 * A simple [AbstractFragment] subclass.
 */
class MarkAttendanceFragment : AbstractFragment<DashboardViewModel>(), LocationUpdateManager.LocationUpdateListener {

    private lateinit var presentOption: RadioButton
    private lateinit var specialOption: RadioButton
    private lateinit var leaveOption: RadioButton
    private lateinit var lblUserAddress: TextView

    private lateinit var userInfo: UserInfo
    private lateinit var mLocationUpdateManager: LocationUpdateManager
    private val PERMISSION_CODE = 1

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfo = AppCache.INSTANCE.getUserInfo()

        mLocationUpdateManager = LocationUpdateManager(baseActivity)
        mLocationUpdateManager.setLocationUpdateListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mark_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presentOption = view.findViewById(R.id.presentOption)
        specialOption = view.findViewById(R.id.specialOption)
        leaveOption = view.findViewById(R.id.leaveOption)
        lblUserAddress = view.findViewById(R.id.lblUserAddress)

        view.findViewById<TextView>(R.id.lblName).text = userInfo.UserId

        view.findViewById<Button>(R.id.btnMarkAttendance).setOnClickListener( {
            validateFields()
        })

        view.findViewById<ImageView>(R.id.btnLogout).setOnClickListener({
            showLogOutDialog(true)
        })

        view.findViewById<LinearLayout>(R.id.btnRefreshLocation).setOnClickListener({
            startLocationUpdate()
        })

        registerObservers()

        if(AppCache.INSTANCE.getLocation() != null && AppCache.INSTANCE.getPlace() != null) {
            showAddress()
        } else {
            startLocationUpdate()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LocationUpdateManager.REQUEST_CHECK_SETTINGS) {
            when(resultCode) {
                Activity.RESULT_OK -> {
                    mLocationUpdateManager.startLocationUpdates()
                }

                Activity.RESULT_CANCELED -> {
                    mLocationUpdateManager.stopLocationUpdates()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationUpdateManager.startLocationUpdates()
            }
        }
    }

    override fun onLocationUpdate(location: Location?) {
        if(location != null) {
            mLocationUpdateManager.stopLocationUpdates()
            AppCache.INSTANCE.setLocation(location)
            val latLng = location.latitude.toString().plus(", ").plus(location.longitude)
            viewModel.getPlace(latLng)
        }
    }

    private fun validateFields() {
        if(AppCache.INSTANCE.getPlace() == null || AppCache.INSTANCE.getPlace()!!.isEmpty()) {
            val errorInfo = ErrorInfo("", getString(R.string.place_fetch_error))
            showError(errorInfo)

        } else {

            if(presentOption.isChecked || leaveOption.isChecked || specialOption.isChecked) {

                var attendance = ""

                if(leaveOption.isChecked) {
                    attendance = "0"

                } else if(presentOption.isChecked) {
                    attendance = "1"

                } else if(specialOption.isChecked) {
                    attendance = "2"
                }

                viewModel.markAttendance(userInfo.id, AppCache.INSTANCE.getPlace()!!, attendance)

            } else {
                val errorInfo = ErrorInfo("", getString(R.string.mark_attendance_selection_error))
                showError(errorInfo)
            }

        }


    }

    private fun registerObservers() {

        viewModel.showLoading().observe(this, Observer {
            showProgressDialog(getString(R.string.loading))
        })

        viewModel.dismissLoading().observe(this, Observer {
            hideProgressDialog()
        })

        viewModel.markAttendanceResponse.observe(this, Observer { message ->
            if(!message.isEmpty()) {
                showSuccess(message)
                viewModel.markAttendanceResponse.value = ""
            }
        })

        viewModel.geoLocationResponse.observe(this, Observer { place ->
            if(!place.isEmpty()) {
                showAddress()
                viewModel.geoLocationResponse.value = ""
            }
        })
    }

    private fun startLocationUpdate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(baseActivity!!,ACCESS_FINE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(baseActivity!!, ACCESS_COARSE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {

                mLocationUpdateManager.startLocationUpdates()

            } else {
                requestPermissions(
                    arrayOf(
                        ACCESS_FINE_LOCATION_PERMISSION,
                        ACCESS_COARSE_LOCATION_PERMISSION
                    ), PERMISSION_CODE
                )
            }

        } else {
            mLocationUpdateManager.startLocationUpdates()
        }
    }

    private fun showAddress() {
        lblUserAddress.text = AppCache.INSTANCE.getPlace()
        lblUserAddress.visibility = View.VISIBLE
    }

}
