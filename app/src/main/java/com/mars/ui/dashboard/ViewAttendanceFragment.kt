package com.mars.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.mars.R
import com.mars.common.base.AbstractFragment
import com.mars.ui.adapters.ViewAttendanceAdapter
import com.mars.utils.AppCache
import com.mars.viewmodels.DashboardViewModel

/**
 * A simple [AbstractFragment] subclass.
 */
class ViewAttendanceFragment : AbstractFragment<DashboardViewModel>() {

    private var adapter: ViewAttendanceAdapter? = null

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ViewAttendanceAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<RecyclerView>(R.id.attendanceGrid)
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
