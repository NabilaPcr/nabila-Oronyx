package com.example.bila_oronyx.Setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import com.example.bila_oronyx.R
import com.example.bila_oronyx.databinding.FragmentSettingBinding
import com.google.android.material.snackbar.Snackbar

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titles = arrayOf(
            "Tampilan & Kecerahan",
            "Notifikasi & Suara",
            "Bahasa (Language)",
            "Keamanan & Kata Sandi",
            "Penyimpanan & Data"
        )

        val subtitles = arrayOf(
            "Atur tingkat kecerahan layar dan tema aplikasi",
            "Kelola nada dering, getaran, dan pop-up peringatan",
            "Ubah bahasa utama aplikasi (Indonesia / English)",
            "Ganti PIN, sidik jari, dan privasi akun Anda",
            "Bersihkan cache dan kelola sisa memori penyimpanan"
        )

        val dataList = ArrayList<HashMap<String, String>>()
        for (i in titles.indices) {
            val map = HashMap<String, String>()
            map["key_title"] = titles[i]
            map["key_subtitle"] = subtitles[i]
            dataList.add(map)
        }

        val from = arrayOf("key_title", "key_subtitle")
        val to = intArrayOf(R.id.textTitle, R.id.textSubtitle)

        val adapter = SimpleAdapter(
            requireContext(),
            dataList,
            R.layout.list_item_settings, // Cetakan kustom buatanmu
            from,
            to
        )

        binding.listMenu.adapter = adapter
        binding.listMenu.setOnItemClickListener { _, _, position, _ ->
            val menuTerpilih = titles[position]
            Snackbar.make(view, "Membuka: $menuTerpilih", Snackbar.LENGTH_SHORT).show()
        }


        binding.btnKirim.setOnClickListener {
            val feedback = binding.inputFeedback.text.toString().trim()

            if (feedback.isNotEmpty()) {
                Snackbar.make(view, "Feedback dikirim!", Snackbar.LENGTH_SHORT).show()
                binding.inputFeedback.text?.clear()
            } else {
                Snackbar.make(view, "Isi feedback dulu", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}