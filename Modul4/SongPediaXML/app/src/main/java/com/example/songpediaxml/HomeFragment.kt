package com.example.songpediaxml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songpediaxml.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SongViewModel by activityViewModels {
        SongViewModelFactory(requireActivity().application, "local")
    }

    private lateinit var songAdapter: SongAdapter
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songAdapter = SongAdapter(
            songs = emptyList(),
            onDetailClick = { song ->
                Timber.d("Tombol Detail ditekan untuk lagu: ${song.title}")
                viewModel.selectSong(song)
            },
            onSpotifyClick = { song ->
                viewModel.onSpotifyClicked(song)
            }
        )

        carouselAdapter = CarouselAdapter(emptyList())

        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSongs.adapter = songAdapter

        binding.rvCarousel.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCarousel.adapter = carouselAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.songs.collect { songs ->
                songAdapter = SongAdapter(
                    songs = songs,
                    onDetailClick = { song ->
                        Timber.d("Tombol Detail ditekan untuk lagu: ${song.title}")
                        viewModel.selectSong(song)
                    },
                    onSpotifyClick = { song ->
                        viewModel.onSpotifyClicked(song)
                    }
                )
                binding.rvSongs.adapter = songAdapter
                carouselAdapter = CarouselAdapter(songs)
                binding.rvCarousel.adapter = carouselAdapter
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToDetail.collect { shouldNavigate ->
                if (shouldNavigate) {
                    findNavController().navigate(R.id.action_home_to_detail)
                    viewModel.onDetailNavigated()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openSpotify.collect { url ->
                if (url != null) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    viewModel.onSpotifyOpened()
                }
            }
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