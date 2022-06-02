package com.aah.sftelehealthworker.ui.doctorCategory.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.R
import com.aah.sftelehealthworker.models.appoinment.Category
import com.aah.sftelehealthworker.utils.GlideApp
import kotlinx.android.synthetic.main.doctor_category_row.view.*
import kotlinx.android.synthetic.main.doctor_category_row.view.image
import kotlinx.android.synthetic.main.doctor_row.view.*

class DoctorCategoryRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
//            return oldItem == newItem
            return oldItem.id == newItem.id
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.doctor_category_row, parent, false), interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Category>) {
        differ.submitList(list)
    }

    class CategoryViewHolder constructor(itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Category) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.category.text = item.title
            val price = "৳ ${item.rate}"
            itemView.price.text = price
            if(item.isAvailable == 1){
                itemView.available.visibility = View.VISIBLE
            }
            else{
                itemView.available.visibility = View.GONE
            }

            GlideApp
                .with(context)
                .load(item.image)
                .fitCenter()
                .placeholder(context.getDrawable(R.drawable.ic_healthcare_and_medical))
                .error(context.getDrawable(R.drawable.ic_healthcare_and_medical))
                .fallback(context.getDrawable(R.drawable.ic_healthcare_and_medical))
                .into(itemView.image)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Category)
    }
}