package com.example.bila_oronyx.Home.pertemuan_10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WargaTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NamaFragment()
            1 -> RumahFragment()
            else -> throw IllegalArgumentException("Posisi tidak valid")
        }
    }
}