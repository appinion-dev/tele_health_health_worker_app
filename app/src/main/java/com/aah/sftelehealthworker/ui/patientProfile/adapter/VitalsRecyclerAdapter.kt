package com.aah.sftelehealthworker.ui.patientProfile.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.newPatient.Vital
import com.aah.sftelehealthworker.utils.AppUtils
import kotlinx.android.synthetic.main.vitals_row.view.*

class VitalsRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Vital>() {

        override fun areItemsTheSame(oldItem: Vital, newItem: Vital): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Vital, newItem: Vital): Boolean {
            return oldItem.id == newItem.id
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VitalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vitals_row, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VitalViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Vital>) {
        differ.submitList(list)
    }

    class VitalViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Vital) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.title.text = "Vitals - " + (layoutPosition + 1)
            val date = AppUtils.changeDateFormatGMT(
                item.createdAt,
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "h:mm a | MMM dd, yyyy"
            )
            itemView.date.text = date
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Vital)
    }
}