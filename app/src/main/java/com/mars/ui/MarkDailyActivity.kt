package com.mars.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mars.R
import com.mars.common.base.AbstractActivity
import com.mars.utils.AppCache
import com.mars.viewmodels.DashboardViewModel

class MarkDailyActivity : AbstractActivity<DashboardViewModel>() {

    private lateinit var txtDailyActivity: EditText

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_daily_activity)

        val supportActionBar = getSupportActionBar()
        supportActionBar?.title = getString(R.string.lblDailyActivity)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black)

        registerObservers()

        txtDailyActivity = findViewById(R.id.txtDailyActivity)

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            if(validateField()) {
                val userInfo = AppCache.INSTANCE.getUserInfo()
                viewModel.markDailyActivity(userInfo.id, txtDailyActivity.text.toString())
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

        viewModel.dailyActivityResponse.observe(this, Observer { message ->
            if(!message.isEmpty()) {
                showSuccess(message)
                viewModel.markAttendanceResponse.value = ""
            }
        })

    }

    private fun validateField(): Boolean {
        return !TextUtils.isEmpty(txtDailyActivity.text.toString().trim())
    }

    override fun markoutAttendance() {

    }

}
