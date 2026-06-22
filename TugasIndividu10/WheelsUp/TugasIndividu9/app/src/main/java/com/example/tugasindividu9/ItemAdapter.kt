package com.example.tugasindividu9

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasindividu9.databinding.ItemCardBinding

class ItemAdapter(
    private val items: List<Item>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.binding.tvId.text = "ID: ${item.id}"
        holder.binding.tvName.text = item.name
        holder.binding.tvInfo.text = item.info

        val isEven = (position + 1) % 2 == 0
        (holder.itemView as CardView).setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                if (isEven) R.color.item_bg_even else R.color.item_bg_odd
            )
        )
    }

    override fun getItemCount() = items.size
}