package com.aah.sftelehealthworker.ui.patientProfile.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.newPatient.Prescription
import com.aah.sftelehealthworker.utils.AppUtils
import kotlinx.android.synthetic.main.vitals_row.view.*

class PrescriptionsRecyclerAdapter(private val interaction: Interaction? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Prescription>() {

        override fun areItemsTheSame(oldItem: Prescription, newItem: Prescription): Boolean {
            return oldItem.caseId == newItem.caseId
        }

        override fun areContentsTheSame(oldItem: Prescription, newItem: Prescription): Boolean {
            return oldItem.caseId == newItem.caseId
        }

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PrescriptionsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vitals_row, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PrescriptionsViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Prescription>) {
        differ.submitList(list)
    }

    class PrescriptionsViewHolder constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Prescription) = with(itemView) {

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.title.text = "Prescription"
            //09:25 AM | Jan 1, 2020
            val date = AppUtils.changeDateFormat(item.consultedAt,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "h:mm a | MMM dd, yyyy")
            itemView.date.text = date

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Prescription)
    }
}