package com.aah.sftelehealthworker.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.home.Patient
import com.aah.sftelehealthworker.utils.GlideApp
import kotlinx.android.synthetic.main.patient_row.view.*
import kotlinx.android.synthetic.main.patient_row.view.image
import kotlinx.android.synthetic.main.patient_row.view.name
import kotlinx.android.synthetic.main.schedule_row.view.*

class PatientsRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Patient>() {

        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PatientsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.patient_row, parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PatientsViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Patient>) {
        differ.submitList(list)
    }

    class PatientsViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Patient) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            GlideApp
                .with(context)
                .load(item.image)
                .circleCrop()
                .placeholder(context.getDrawable(R.drawable.person_male))
                .error(context.getDrawable(R.drawable.person_male))
                .fallback(context.getDrawable(R.drawable.person_male))
                .into(itemView.image)

            itemView.name.text = item.firstName + " " + item.lastName
            itemView.age.text = item.age + " Years"
            itemView.sex.text = item.gender
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Patient)
    }
}