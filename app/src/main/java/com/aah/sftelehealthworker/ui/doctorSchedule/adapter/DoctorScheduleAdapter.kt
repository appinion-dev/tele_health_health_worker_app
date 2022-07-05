package com.aah.sftelehealthworker.ui.doctorSchedule.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.appoinment.TimeSlot
import com.aah.sftelehealthworker.utils.AppUtils
import kotlinx.android.synthetic.main.time_slot_row.view.*

class DoctorScheduleAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TimeSlot>() {

        override fun areItemsTheSame(oldItem: TimeSlot, newItem: TimeSlot): Boolean {
            return oldItem.timeslotId == newItem.timeslotId
        }

        override fun areContentsTheSame(oldItem: TimeSlot, newItem: TimeSlot): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DoctorScheduleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.time_slot_row, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DoctorScheduleViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<TimeSlot>) {
        differ.submitList(list)
    }

    class DoctorScheduleViewHolder constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TimeSlot) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            //08-10-2020 01:00 pm
            itemView.name.text = item.doctorName
            val startTime = AppUtils.changeDateFormat(item.startTime, "dd-MM-yyyy h:mm a", "h:mm a")
            val endTime = AppUtils.changeDateFormat(item.endTime, "dd-MM-yyyy h:mm a", "h:mm a")
            val datetime = "$startTime TO $endTime"
            itemView.date.text = datetime
//            itemView.available.text = item.doctorName
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: TimeSlot)
    }
}