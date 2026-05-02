package com.example.songpediaxml

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.songpediaxml.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SongViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("language", "id")

        viewModel.selectedSong.observe(viewLifecycleOwner) { song ->
            binding.tvDetailTitle.text = song.title
            binding.tvDetailArtist.text = song.artist
            binding.tvDetailYear.text = song.year.toString()
            binding.tvDetailAlbum.text = song.album
            binding.tvDetailGenre.text = song.genre

            if (lang == "id") {
                binding.tvDescriptionLabel.text = getString(R.string.label_description)
                binding.tvDetailDescription.text = song.descriptionId
            } else {
                binding.tvDescriptionLabel.text = getString(R.string.label_description)
                binding.tvDetailDescription.text = song.descriptionEn
            }

            Glide.with(requireContext())
                .load(song.imageRes)
                .transform(RoundedCorners(32))
                .into(binding.imgDetailAlbum)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}