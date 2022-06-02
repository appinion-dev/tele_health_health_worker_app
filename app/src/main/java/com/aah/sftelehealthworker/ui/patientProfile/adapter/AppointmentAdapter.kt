package com.aah.sftelehealthworker.ui.patientProfile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.patient.Appointment
import com.aah.sftelehealthworker.utils.DateUtils
import com.aah.sftelehealthworker.utils.GlideApp
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.schedule_row.view.*
import java.util.*

class AppointmentAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Appointment>() {

        override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private var glide: RequestManager? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ScheduleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.schedule_row,
                parent,
                false
            ), interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScheduleViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Appointment>) {
        differ.submitList(list)
    }



    fun setGlideRequestManager(glide: RequestManager) {
        this.glide = glide
    }

    class ScheduleViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(
            itemView
        ) {

        fun bind(item: Appointment) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            GlideApp
                .with(context)
                .load(item.doctorImage)
                .circleCrop()
                .placeholder(context.getDrawable(R.drawable.phone_icon_dark))
                .error(context.getDrawable(R.drawable.phone_icon_dark))
                .fallback(context.getDrawable(R.drawable.phone_icon_dark))
                .into(itemView.image)

            itemView.name.text = item.doctorName
            val callbackDate = DateUtils.currentDate(item.callbackDate + " " + item.startTime)

            itemView.date.text = callbackDate
            itemView.status.text = item.status

            if (item.documentUrl != null) {
                if (item.documentUrl) {
                    itemView.attachment.visibility = View.VISIBLE
                } else {
                    itemView.attachment.visibility = View.GONE
                }
            } else {
                itemView.attachment.visibility = View.GONE
            }

            itemView.attachment.setOnClickListener {
                interaction!!.onAttachmentClick(adapterPosition, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Appointment)
        fun onAttachmentClick(position: Int, item: Appointment)
    }
}