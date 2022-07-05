package com.aah.sftelehealthworker.ui.patientProfile.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.newPatient.Report
import com.aah.sftelehealthworker.utils.AppUtils
import kotlinx.android.synthetic.main.report_row.view.*
import kotlinx.android.synthetic.main.vitals_row.view.*
import kotlinx.android.synthetic.main.vitals_row.view.date
import kotlinx.android.synthetic.main.vitals_row.view.title
import java.util.*

class ReportRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val calender: Calendar = Calendar.getInstance()

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Report>() {

        override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem.id == newItem.id
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private var displayList: MutableList<Report> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ReportViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.report_row, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReportViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Report>) {
        differ.submitList(list)
        displayList = list.toMutableList()
    }

    class ReportViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Report) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.title.text = item.title
            val date = AppUtils.changeDateFormat(item.createdAt,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "h:mm a | MMM dd, yyyy")
            itemView.date.text = date
            itemView.note.text = item.note
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Report)
    }
}