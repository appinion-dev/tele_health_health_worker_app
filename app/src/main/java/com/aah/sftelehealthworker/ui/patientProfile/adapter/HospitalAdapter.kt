package com.aah.sftelehealthworker.ui.patientProfile.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.aah.sftelehealthworker.models.referHospital.Hospital
import androidx.recyclerview.widget.RecyclerView
import com.aah.sftelehealthworker.ui.patientProfile.adapter.HospitalAdapter.MyViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.aah.sftelehealthworker.R
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aah.sftelehealthworker.models.patient.Appointment
import com.bumptech.glide.RequestManager

class HospitalAdapter(var hospitalLists: ArrayList<Hospital>) :
    RecyclerView.Adapter<MyViewHolder>() {
//    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hospital>() {
//
//        override fun areItemsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        @SuppressLint("DiffUtilEquals")
//        override fun areContentsTheSame(oldItem: Hospital, newItem: Hospital): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
//    private var glide: RequestManager? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rowItem =
            LayoutInflater.from(parent.context).inflate(R.layout.row_hospital_item, parent, false)
        return MyViewHolder(rowItem)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mHospitalList = hospitalLists[position]
        if (mHospitalList != null) {
            //holder.itemId.setText(String.valueOf(mHospitalList.getId()+"."));
            holder.itemName.text = "Hospital Name: " + mHospitalList.name
            holder.number.text = "Phone: " + mHospitalList.emergencyPhone
            holder.address.text = "Address: " + mHospitalList.address
        }
    }

    override fun getItemCount(): Int {
        return hospitalLists.size
    }
    fun submitList(list: List<Hospital>): List<Hospital> {
        this.hospitalLists = list as ArrayList<Hospital>
        return list;
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView
        var itemId: TextView? = null
        var number: TextView
        var address: TextView

        init {
            //itemId = itemView.findViewById(R.id.itemId);
            itemName = itemView.findViewById(R.id.itemName)
            number = itemView.findViewById(R.id.phone)
            address = itemView.findViewById(R.id.address)
        }
    }
}