package com.example.bila_oronyx.pertemuan_10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bila_oronyx.Home.Warga.NamaFragment
import com.example.bila_oronyx.Home.Warga.RumahFragment

class WargaTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah total tab yang ada (Nama Warga dan Rumah Warga)
    override fun getItemCount(): Int = 2

    // Menentukan Fragment mana yang akan ditampilkan berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NamaFragment()
            1 -> RumahFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}