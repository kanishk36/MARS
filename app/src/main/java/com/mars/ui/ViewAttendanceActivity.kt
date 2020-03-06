package com.mars.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.mars.R
import com.mars.common.base.AbstractActivity
import com.mars.ui.adapters.ViewAttendanceAdapter
import com.mars.utils.AppCache
import com.mars.viewmodels.DashboardViewModel

class ViewAttendanceActivity : AbstractActivity<DashboardViewModel>() {

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

    private var adapter: ViewAttendanceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_attendance)

        val supportActionBar = getSupportActionBar()
        supportActionBar?.title = getString(R.string.lblViewAttendance)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black)

        adapter = ViewAttendanceAdapter()

        val listView = findViewById<RecyclerView>(R.id.attendanceGrid)
        listView.adapter = adapter

        registerObservers()

        viewModel.viewAttendance(AppCache.INSTANCE.getUserInfo().id)
    }

    private fun registerObservers() {

        viewModel.showLoading().observe(this, Observer {
            showProgressDialog(getString(R.string.loading))
        })

        viewModel.dismissLoading().observe(this, Observer {
            hideProgressDialog()
        })

        viewModel.viewAttendanceResponse.observe(this, Observer {
            adapter?.setAttendanceList(it)
        })
    }
}
