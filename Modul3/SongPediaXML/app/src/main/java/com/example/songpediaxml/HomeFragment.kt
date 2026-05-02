package com.example.songpediaxml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songpediaxml.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SongViewModel by activityViewModels()
    private lateinit var songAdapter: SongAdapter
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songAdapter = SongAdapter(
            songs = emptyList(),
            onDetailClick = { song ->
                viewModel.selectSong(song)
                findNavController().navigate(R.id.action_home_to_detail)
            },
            onSpotifyClick = { song ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(song.spotifyUrl))
                startActivity(intent)
            }
        )

        carouselAdapter = CarouselAdapter(emptyList())

        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSongs.adapter = songAdapter

        binding.rvCarousel.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvCarousel.adapter = carouselAdapter

        viewModel.songs.observe(viewLifecycleOwner) { songs ->
            songAdapter = SongAdapter(
                songs = songs,
                onDetailClick = { song ->
                    viewModel.selectSong(song)
                    findNavController().navigate(R.id.action_home_to_detail)
                },
                onSpotifyClick = { song ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(song.spotifyUrl))
                    startActivity(intent)
                }
            )
            binding.rvSongs.adapter = songAdapter
            carouselAdapter = CarouselAdapter(songs)
            binding.rvCarousel.adapter = carouselAdapter
        }

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_language)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}