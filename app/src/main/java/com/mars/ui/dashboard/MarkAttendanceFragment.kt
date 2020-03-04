package com.mars.ui.dashboard


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mars.R
import com.mars.common.base.AbstractFragment
import com.mars.models.UserInfo
import com.mars.network.ErrorInfo
import com.mars.utils.AppCache
import com.mars.viewmodels.DashboardViewModel

/**
 * A simple [AbstractFragment] subclass.
 */
class MarkAttendanceFragment : AbstractFragment<DashboardViewModel>() {

    private lateinit var presentOption: RadioButton
    private lateinit var specialOption: RadioButton
    private lateinit var leaveOption: RadioButton

    private lateinit var userInfo: UserInfo

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfo = AppCache.INSTANCE.getUserInfo()
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

        view.findViewById<TextView>(R.id.lblName).text = userInfo.UserId

        view.findViewById<Button>(R.id.btnMarkAttendance).setOnClickListener( {
            validateFields()
        })

        view.findViewById<ImageView>(R.id.btnLogout).setOnClickListener({
            logOut()
        })

        registerObservers()
    }

    private fun validateFields() {
        if(presentOption.isChecked || leaveOption.isChecked || specialOption.isChecked) {

            var attendance = ""

            if(leaveOption.isChecked) {
                attendance = "0"

            } else if(presentOption.isChecked) {
                attendance = "1"

            } else if(specialOption.isChecked) {
                attendance = "2"
            }

            viewModel.markAttendance(userInfo.id, "kanpur", attendance)

        } else {
            val errorInfo = ErrorInfo("", getString(R.string.mark_attendance_selection_error))
            showError(errorInfo)
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
            showSuccess(message)
            viewModel.markAttendanceResponse.value = null
        })
    }

}
