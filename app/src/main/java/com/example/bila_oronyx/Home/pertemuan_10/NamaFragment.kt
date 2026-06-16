// 📁 Home/pertemuan_10/NamaFragment.kt
package com.example.bila_oronyx.Home.pertemuan_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bila_oronyx.data.AppDatabase
import com.example.bila_oronyx.data.entity.WargaEntity
import com.example.bila_oronyx.databinding.FragmentNamaBinding
import kotlinx.coroutines.launch

class NamaFragment : Fragment() {

    private var _binding: FragmentNamaBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: WargaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNamaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        // Setup RecyclerView dengan Grid 2 kolom
        binding.rvWarga.layoutManager = GridLayoutManager(requireContext(), 2)

        // Tambahkan data awal jika belum ada
        insertSampleDataIfEmpty()

        loadWargaData()
    }

    private fun insertSampleDataIfEmpty() {
        lifecycleScope.launch {
            val existingData = db.wargaDao().getAll()
            if (existingData.isEmpty()) {
                // Data contoh
                val sampleData = listOf(
                    WargaEntity(
                        nama = "Ahmad Fauzi",
                        nik = "1234567890123456",
                        alamat = "Jl. Merdeka No. 10",
                        rt = "01",
                        rw = "03",
                        noTelepon = "081234567890",
                        status = "Aktif",
                        createdAt = System.currentTimeMillis()
                    ),
                    WargaEntity(
                        nama = "Siti Rahayu",
                        nik = "2345678901234567",
                        alamat = "Jl. Sudirman No. 5",
                        rt = "01",
                        rw = "03",
                        noTelepon = "081234567891",
                        status = "Aktif",
                        createdAt = System.currentTimeMillis()
                    ),
                    WargaEntity(
                        nama = "Bambang Sutrisno",
                        nik = "3456789012345678",
                        alamat = "Jl. Diponegoro No. 15",
                        rt = "02",
                        rw = "03",
                        noTelepon = "081234567892",
                        status = "Aktif",
                        createdAt = System.currentTimeMillis()
                    ),
                    WargaEntity(
                        nama = "Dewi Kartika",
                        nik = "4567890123456789",
                        alamat = "Jl. Pahlawan No. 20",
                        rt = "02",
                        rw = "03",
                        noTelepon = "081234567893",
                        status = "Pindah",
                        createdAt = System.currentTimeMillis()
                    ),
                    WargaEntity(
                        nama = "Eko Prasetyo",
                        nik = "5678901234567890",
                        alamat = "Jl. Siliwangi No. 8",
                        rt = "03",
                        rw = "04",
                        noTelepon = "081234567894",
                        status = "Aktif",
                        createdAt = System.currentTimeMillis()
                    ),
                    WargaEntity(
                        nama = "Fitri Handayani",
                        nik = "6789012345678901",
                        alamat = "Jl. Ahmad Yani No. 12",
                        rt = "03",
                        rw = "04",
                        noTelepon = "081234567895",
                        status = "Aktif",
                        createdAt = System.currentTimeMillis()
                    )
                )
                sampleData.forEach { db.wargaDao().insert(it) }
            }
        }
    }

    private fun loadWargaData() {
        lifecycleScope.launch {
            val wargaList = db.wargaDao().getAll()
            adapter = WargaAdapter(wargaList) { warga ->
                Toast.makeText(
                    requireContext(),
                    "Memilih: ${warga.nama}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.rvWarga.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        loadWargaData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}