package com.example.songpediaxml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.songpediaxml.databinding.ItemCarouselBinding

class CarouselAdapter(
    private val songs: List<Song>
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(val binding: ItemCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val song = songs[position]
        Glide.with(holder.binding.root.context)
            .load(song.imageRes)
            .transform(RoundedCorners(32))
            .into(holder.binding.imgCarousel)
    }

    override fun getItemCount() = songs.size
}