package com.mars.ui.adapters

import android.text.AutoText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mars.R
import com.mars.models.AttendanceModel

class ViewAttendanceAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var mAttendanceList: MutableList<AttendanceModel>

    init {
        mAttendanceList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_attendance_item_layout, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun getItemCount(): Int {
        var count = 0
        if(mAttendanceList.size > 0) {
            count = mAttendanceList.size + 1
        }
        return count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position > 0) {
            val attendanceModel = mAttendanceList[position-1]
            val attendanceViewHolder = holder as AttendanceViewHolder

            attendanceViewHolder.lblSrNo.text = position.toString()
            attendanceViewHolder.lblCName.text = attendanceModel.cname
            attendanceViewHolder.lblUName.text = attendanceModel.uname
            attendanceViewHolder.lblDesignation.text = attendanceModel.designation
            attendanceViewHolder.lblLocation.text = attendanceModel.location
            attendanceViewHolder.lblDate.text = attendanceModel.date
            attendanceViewHolder.lblTime.text = attendanceModel.time
        }
    }

    fun setAttendanceList(@NonNull attendanceList: ArrayList<AttendanceModel>) {
        mAttendanceList = attendanceList
        notifyDataSetChanged()
    }

    private inner class AttendanceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var lblSrNo: TextView
        var lblCName: TextView
        var lblUName: TextView
        var lblDesignation: TextView
        var lblLocation: TextView
        var lblDate: TextView
        var lblTime: TextView
        var lblComment: TextView

        init {
            lblSrNo = itemView.findViewById(R.id.lblSrNo)
            lblCName = itemView.findViewById(R.id.lblCName)
            lblUName = itemView.findViewById(R.id.lblUName)
            lblDesignation = itemView.findViewById(R.id.lblDesignation)
            lblLocation = itemView.findViewById(R.id.lblLocation)
            lblDate = itemView.findViewById(R.id.lblDate)
            lblTime = itemView.findViewById(R.id.lblTime)
            lblComment = itemView.findViewById(R.id.lblComment)
        }
    }
}