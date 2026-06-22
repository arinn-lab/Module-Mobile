package com.example.latihanrecyclerview

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanrecyclerview.databinding.ItemLayoutBinding

class ItemAdapter(
    private val items: List<Item>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val b = holder.binding
        val context = holder.itemView.context

        b.imgItem.setImageResource(item.imageRes)
        b.tvTitle.text = item.title
        b.tvDescription.text = item.description

        b.switchToggle.setOnCheckedChangeListener(null)
        b.switchToggle.isChecked = item.isToggled
        updateSwitchColor(holder)

        val isEven = (position + 1) % 2 == 0
        (holder.itemView as CardView).setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                if (isEven) R.color.item_bg_even else R.color.item_bg_odd
            )
        )

        holder.itemView.setOnClickListener {
            Toast.makeText(
                context,
                context.getString(R.string.toast_item_click, item.id),
                Toast.LENGTH_SHORT
            ).show()
        }

        b.switchToggle.setOnCheckedChangeListener { _, isChecked ->
            item.isToggled = isChecked
            updateSwitchColor(holder)
            if (isChecked) {
                Toast.makeText(
                    context,
                    context.getString(R.string.toast_switch_on, item.id),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        b.btnAksi.setOnClickListener {
            Toast.makeText(
                context,
                context.getString(R.string.toast_btn_aksi, item.id),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = items.size

    private fun updateSwitchColor(holder: ItemViewHolder) {
        val context = holder.itemView.context
        val colorActive = ContextCompat.getColor(context, R.color.color_primary)
        val colorTrackInactive = ContextCompat.getColor(context, R.color.switch_track_inactive)
        val colorThumbInactive = ContextCompat.getColor(context, R.color.switch_thumb_inactive)
        val colorThumbActive = ContextCompat.getColor(context, R.color.switch_thumb_active)

        val trackColors = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(colorTrackInactive, colorActive)
        )

        val thumbColors = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(colorThumbInactive, colorThumbActive)
        )

        holder.binding.switchToggle.trackTintList = trackColors
        holder.binding.switchToggle.thumbTintList = thumbColors
    }
}