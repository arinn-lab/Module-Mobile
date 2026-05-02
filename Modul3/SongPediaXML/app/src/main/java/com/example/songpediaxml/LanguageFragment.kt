package com.example.songpediaxml

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.songpediaxml.databinding.FragmentLanguageBinding
import java.util.Locale

class LanguageFragment : Fragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val currentLang = prefs.getString("language", "id")

        updateCheckmarks(currentLang)

        binding.cardIndonesia.setOnClickListener {
            prefs.edit().putString("language", "id").apply()
            setLocale("id")
        }

        binding.cardEnglish.setOnClickListener {
            prefs.edit().putString("language", "en").apply()
            setLocale("en")
        }
    }

    private fun updateCheckmarks(lang: String?) {
        if (lang == "id") {
            binding.checkIndonesia.visibility = View.VISIBLE
            binding.checkEnglish.visibility = View.GONE
        } else {
            binding.checkEnglish.visibility = View.VISIBLE
            binding.checkIndonesia.visibility = View.GONE
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)
        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}