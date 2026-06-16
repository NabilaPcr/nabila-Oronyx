// 📁 Home/pertemuan_10/RumahFragment.kt
package com.example.bila_oronyx.Home.pertemuan_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bila_oronyx.data.AppDatabase
import com.example.bila_oronyx.data.entity.WargaEntity
import com.example.bila_oronyx.databinding.FragmentRumahBinding
import kotlinx.coroutines.launch

class RumahFragment : Fragment() {

    private var _binding: FragmentRumahBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: RumahAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRumahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        // Setup RecyclerView dengan Linear
        binding.rvRumah.layoutManager = LinearLayoutManager(requireContext())

        loadRumahData()
    }

    private fun loadRumahData() {
        lifecycleScope.launch {
            val wargaList = db.wargaDao().getAll()
            val rumahList = wargaList.map { warga ->
                RumahModel(
                    namaRumah = "Rumah ${warga.nama}",
                    alamat = warga.alamat,
                    rt = warga.rt,
                    rw = warga.rw,
                    gambar = "https://picsum.photos/seed/rumah${warga.id}/400/300"
                )
            }

            adapter = RumahAdapter(rumahList) { rumah ->
                Toast.makeText(
                    requireContext(),
                    "Memilih: ${rumah.namaRumah}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.rvRumah.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        loadRumahData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}