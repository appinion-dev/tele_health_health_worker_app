package com.aah.sftelehealthworker.ui.patientProfile.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.Assessment
import kotlinx.android.synthetic.main.assessment_row.view.*

class AssessmentRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Assessment>() {

        override fun areItemsTheSame(oldItem: Assessment, newItem: Assessment): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Assessment, newItem: Assessment): Boolean {
            return oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return AssessmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.assessment_row, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AssessmentViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Assessment>) {
        differ.submitList(list)
    }

    class AssessmentViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Assessment) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.name.text = item.name
            itemView.time.text = item.time
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Assessment)
    }
}