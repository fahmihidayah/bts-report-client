package com.polytech.btsreport.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polytech.btsreport.data.dto.response.Visitation
import com.polytech.btsreport.databinding.VisitationItemBinding

class VisitationAdapter(
    private val onItemClick: (Visitation) -> Unit
) : ListAdapter<Visitation, VisitationAdapter.VisitationViewHolder>(VisitationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitationViewHolder {
        val binding = VisitationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VisitationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisitationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VisitationViewHolder(
        private val binding: VisitationItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(visitation: Visitation) {
            binding.visitation = visitation
            binding.executePendingBindings()

            binding.buttonDetails.setOnClickListener {
                onItemClick(visitation)
            }
        }
    }

    private class VisitationDiffCallback : DiffUtil.ItemCallback<Visitation>() {
        override fun areItemsTheSame(oldItem: Visitation, newItem: Visitation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Visitation, newItem: Visitation): Boolean {
            return oldItem == newItem
        }
    }
}
