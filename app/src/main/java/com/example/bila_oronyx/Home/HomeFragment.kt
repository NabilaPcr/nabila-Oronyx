package com.example.bila_oronyx.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bila_oronyx.BeritaWebActivity
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.FragmentHomeBinding
import com.example.bila_oronyx.pertemuan2.RumusBangun
import com.example.bila_oronyx.pertemuan3.LoginOronyx
import com.example.bila_oronyx.pertemuan4.ListBelajar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRumus.setOnClickListener {
            startActivity(Intent(requireContext(), RumusBangun::class.java))
        }

        binding.btnList.setOnClickListener {
            startActivity(Intent(requireContext(), ListBelajar::class.java))
        }

        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), BeritaWebActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
                    sharedPref.edit().clear().apply()
                    startActivity(Intent(requireContext(), LoginOronyx::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}