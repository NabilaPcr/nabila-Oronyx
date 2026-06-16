// 📁 Home/pertemuan_10/RumahAdapter.kt
package com.example.bila_oronyx.Home.pertemuan_10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bila_oronyx.databinding.ItemRumahBinding

class RumahAdapter(
    private val rumahList: List<RumahModel>,
    private val onItemClick: (RumahModel) -> Unit
) : RecyclerView.Adapter<RumahAdapter.RumahViewHolder>() {

    inner class RumahViewHolder(val binding: ItemRumahBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RumahViewHolder {
        val binding = ItemRumahBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RumahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RumahViewHolder, position: Int) {
        val rumah = rumahList[position]

        holder.binding.apply {
            tvNamaRumah.text = rumah.namaRumah
            tvAlamatRumah.text = rumah.alamat
            tvRtRw.text = "RT ${rumah.rt} / RW ${rumah.rw}"

            Glide.with(holder.itemView.context)
                .load(rumah.gambar)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgRumah)

            root.setOnClickListener {
                onItemClick(rumah)
            }
        }
    }

    override fun getItemCount(): Int = rumahList.size
}