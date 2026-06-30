package com.example.bila_oronyx.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bila_oronyx.BeritaWebActivity
import com.example.bila_oronyx.databinding.FragmentHomeBinding
import com.example.bila_oronyx.Home.pertemuan2.RumusBangun
import com.example.bila_oronyx.Home.pertemuan3.LoginOronyx
import com.example.bila_oronyx.Home.pertemuan4.ListBelajar
import com.example.bila_oronyx.Home.pertemuan_10.WargaActivity
import com.example.bila_oronyx.Home.news.NewsAdapter
// PERBAIKAN 1: Menyesuaikan package import ApiClient yang benar
import com.example.bila_oronyx.data.api.NewsApiClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import android.widget.Toast
import com.example.bila_oronyx.Profile.ProfileFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var newsAdapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupRecyclerView()
        loadNews()
        setupRefreshNews()
    }

    private fun setupButtons() {
        binding.btnRumus.setOnClickListener {
            startActivity(Intent(requireContext(), RumusBangun::class.java))
        }

        binding.btnList.setOnClickListener {
            startActivity(Intent(requireContext(), ListBelajar::class.java))
        }

        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), BeritaWebActivity::class.java))
        }

        binding.btnWarga.setOnClickListener {
            startActivity(Intent(requireContext(), WargaActivity::class.java))
        }

        binding.btnInfo.setOnClickListener {
            startActivity(Intent(requireContext(), BeritaWebActivity::class.java))
        }

        binding.btnProfil.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileFragment::class.java))
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    sharedPref.edit().clear().apply()
                    startActivity(Intent(requireContext(), LoginOronyx::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    private fun setupRecyclerView() {
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupRefreshNews() {
        binding.tvRefreshNews.setOnClickListener {
            loadNews()
        }
    }

    private fun loadNews() {
        lifecycleScope.launch {
            try {
                // Tampilkan Loading Component
                binding.progressBarNews.visibility = View.VISIBLE
                binding.rvNews.visibility = View.GONE
                binding.tvEmptyNews.visibility = View.GONE

                val apiKey = NewsApiClient.getApiKey()
                val newsResponse = NewsApiClient.apiService.getNews(country = "id", apiKey = apiKey)

                if (newsResponse.status == "ok" && newsResponse.articles.isNotEmpty()) {
                    val limitedArticles = newsResponse.articles.take(5)

                    newsAdapter = NewsAdapter(limitedArticles)

                    binding.rvNews.adapter = newsAdapter
                    binding.rvNews.visibility = View.VISIBLE
                } else {
                    binding.tvEmptyNews.visibility = View.VISIBLE
                }

                binding.progressBarNews.visibility = View.GONE

            } catch (e: Exception) {
                binding.progressBarNews.visibility = View.GONE
                binding.tvEmptyNews.visibility = View.VISIBLE
                binding.tvEmptyNews.text = "Gagal memuat berita: ${e.message}"
                Toast.makeText(requireContext(), "Gagal memuat berita", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}