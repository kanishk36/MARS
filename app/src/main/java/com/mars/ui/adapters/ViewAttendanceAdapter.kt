package com.mars.ui.adapters

import android.graphics.Typeface
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

        val attendanceViewHolder = holder as AttendanceViewHolder

        if(position > 0) {
            val attendanceModel = mAttendanceList[position-1]

            attendanceViewHolder.lblSrNo.text = position.toString()
            attendanceViewHolder.lblCName.text = attendanceModel.cname
            attendanceViewHolder.lblUName.text = attendanceModel.uname
            attendanceViewHolder.lblDesignation.text = attendanceModel.designation
            attendanceViewHolder.lblLocation.text = attendanceModel.location
            attendanceViewHolder.lblDate.text = attendanceModel.date
            attendanceViewHolder.lblTime.text = attendanceModel.time
            attendanceViewHolder.lblAttendance.text = attendanceModel.aattendance
            attendanceViewHolder.lblComment.text = attendanceModel.comment

            attendanceViewHolder.lblSrNo.setTypeface(attendanceViewHolder.lblSrNo.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblCName.setTypeface(attendanceViewHolder.lblCName.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblUName.setTypeface(attendanceViewHolder.lblUName.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblDesignation.setTypeface(attendanceViewHolder.lblDesignation.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblLocation.setTypeface(attendanceViewHolder.lblLocation.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblDate.setTypeface(attendanceViewHolder.lblDate.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblTime.setTypeface(attendanceViewHolder.lblTime.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblAttendance.setTypeface(attendanceViewHolder.lblAttendance.typeface, Typeface.NORMAL)
            attendanceViewHolder.lblComment.setTypeface(attendanceViewHolder.lblComment.typeface, Typeface.NORMAL)

        } else {

            val resources = holder.itemView.context.resources

            attendanceViewHolder.lblSrNo.text = resources.getString(R.string.SNo)
            attendanceViewHolder.lblCName.text = resources.getString(R.string.name_of_CLTC)
            attendanceViewHolder.lblUName.text = resources.getString(R.string.name_of_ULBs)
            attendanceViewHolder.lblDesignation.text = resources.getString(R.string.designation)
            attendanceViewHolder.lblLocation.text = resources.getString(R.string.location)
            attendanceViewHolder.lblDate.text = resources.getString(R.string.date)
            attendanceViewHolder.lblTime.text = resources.getString(R.string.time)
            attendanceViewHolder.lblAttendance.text = resources.getString(R.string.attendance)
            attendanceViewHolder.lblComment.text = resources.getString(R.string.comment_header)

            attendanceViewHolder.lblSrNo.setTypeface(attendanceViewHolder.lblSrNo.typeface, Typeface.BOLD)
            attendanceViewHolder.lblCName.setTypeface(attendanceViewHolder.lblCName.typeface, Typeface.BOLD)
            attendanceViewHolder.lblUName.setTypeface(attendanceViewHolder.lblUName.typeface, Typeface.BOLD)
            attendanceViewHolder.lblDesignation.setTypeface(attendanceViewHolder.lblDesignation.typeface, Typeface.BOLD)
            attendanceViewHolder.lblLocation.setTypeface(attendanceViewHolder.lblLocation.typeface, Typeface.BOLD)
            attendanceViewHolder.lblDate.setTypeface(attendanceViewHolder.lblDate.typeface, Typeface.BOLD)
            attendanceViewHolder.lblTime.setTypeface(attendanceViewHolder.lblTime.typeface, Typeface.BOLD)
            attendanceViewHolder.lblAttendance.setTypeface(attendanceViewHolder.lblAttendance.typeface, Typeface.BOLD)
            attendanceViewHolder.lblComment.setTypeface(attendanceViewHolder.lblComment.typeface, Typeface.BOLD)

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
        var lblAttendance: TextView
        var lblComment: TextView

        init {
            lblSrNo = itemView.findViewById(R.id.lblSrNo)
            lblCName = itemView.findViewById(R.id.lblCName)
            lblUName = itemView.findViewById(R.id.lblUName)
            lblDesignation = itemView.findViewById(R.id.lblDesignation)
            lblLocation = itemView.findViewById(R.id.lblLocation)
            lblDate = itemView.findViewById(R.id.lblDate)
            lblTime = itemView.findViewById(R.id.lblTime)
            lblAttendance = itemView.findViewById(R.id.lblAttendance)
            lblComment = itemView.findViewById(R.id.lblComment)
        }
    }
}