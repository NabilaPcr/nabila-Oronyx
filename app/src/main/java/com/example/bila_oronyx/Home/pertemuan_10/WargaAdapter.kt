// 📁 Home/pertemuan_10/WargaAdapter.kt
package com.example.bila_oronyx.Home.pertemuan_10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bila_oronyx.data.entity.WargaEntity
import com.example.bila_oronyx.databinding.ItemWargaBinding

class WargaAdapter(
    private val wargaList: List<WargaEntity>,
    private val onItemClick: (WargaEntity) -> Unit
) : RecyclerView.Adapter<WargaAdapter.WargaViewHolder>() {

    inner class WargaViewHolder(val binding: ItemWargaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WargaViewHolder {
        val binding = ItemWargaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WargaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WargaViewHolder, position: Int) {
        val warga = wargaList[position]

        holder.binding.apply {
            tvNama.text = warga.nama
            tvNik.text = "NIK: ${warga.nik}"
            tvAlamat.text = warga.alamat
            tvStatus.text = warga.status

            // Warna status
            when (warga.status.lowercase()) {
                "aktif" -> tvStatus.setTextColor(
                    holder.itemView.context.getColor(android.R.color.holo_green_dark)
                )
                "pindah" -> tvStatus.setTextColor(
                    holder.itemView.context.getColor(android.R.color.holo_orange_dark)
                )
                "meninggal" -> tvStatus.setTextColor(
                    holder.itemView.context.getColor(android.R.color.holo_red_dark)
                )
            }

            root.setOnClickListener {
                onItemClick(warga)
            }
        }
    }

    override fun getItemCount(): Int = wargaList.size
}