package com.yuan.order.manage.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuan.order.databinding.RecyclerStaffBinding

class MStaffAdapter : RecyclerView.Adapter<MStaffAdapter.StaffViewHolder>() {
    class StaffViewHolder(val binding: RecyclerStaffBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerStaffBinding.inflate(layoutInflater, parent, false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = 1
}