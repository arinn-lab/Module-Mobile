package com.example.songpediaxml

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.songpediaxml.databinding.ItemSongBinding

class SongAdapter(
    private var songs: List<Song>,
    private val onDetailClick: (Song) -> Unit,
    private val onSpotifyClick: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        val context = holder.binding.root.context

        holder.binding.tvTitle.text = song.title
        holder.binding.tvArtist.text = song.artist
        holder.binding.tvYear.text = song.year.toString()

        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("language", "id")
        if (lang == "id") {
            holder.binding.tvDescription.text = song.descriptionId
        } else {
            holder.binding.tvDescription.text = song.descriptionEn
        }

        Glide.with(context)
            .load(song.imageRes)
            .transform(RoundedCorners(24))
            .into(holder.binding.imgAlbum)

        holder.binding.btnSpotify.setOnClickListener {
            onSpotifyClick(song)
        }

        holder.binding.btnDetail.setOnClickListener {
            onDetailClick(song)
        }
    }

    override fun getItemCount() = songs.size
}