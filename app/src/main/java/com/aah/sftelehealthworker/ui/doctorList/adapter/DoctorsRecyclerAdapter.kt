package com.aah.sftelehealthworker.ui.doctorList.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.appoinment.Doctor
import com.aah.sftelehealthworker.utils.GlideApp
import kotlinx.android.synthetic.main.doctor_row.view.*
import kotlinx.android.synthetic.main.doctor_row.view.image
import kotlinx.android.synthetic.main.doctor_row.view.name
import kotlinx.android.synthetic.main.patient_row.view.*
import java.util.*

class DoctorsRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Doctor>() {

        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
//            return oldItem == newItem
            return oldItem.id == newItem.id
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DoctorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.doctor_row, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DoctorViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Doctor>) {
        differ.submitList(list)
    }

    class DoctorViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Doctor) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            val rate = "৳ ${item.callbackRate}"
            itemView.rate.text = rate
            val name = "${item.title} ${item.firstName} ${item.lastName}"
            itemView.name.text = name
            itemView.doctorDegree.text = item.qualification
            itemView.experience.text = getExperience(item.workingSince)
            itemView.workPlace.text = item.hospital
            itemView.location.text = item.location

            GlideApp
                .with(context)
                .load(item.image)
                .centerCrop()
                .placeholder(context.getDrawable(R.drawable.ic_healthcare_and_medical))
                .error(context.getDrawable(R.drawable.ic_healthcare_and_medical))
                .fallback(context.getDrawable(R.drawable.ic_healthcare_and_medical))
                .into(itemView.image)
        }

        private fun getExperience(workingSince: Int): String {
            val presentYear = Calendar.getInstance().get(Calendar.YEAR)
            val expYear = (presentYear - workingSince)
            return  "EXP $expYear YEARS"
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Doctor)
    }
}