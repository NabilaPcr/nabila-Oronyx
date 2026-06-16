package com.example.bila_oronyx.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warga")
data class WargaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val nik: String,
    val alamat: String,
    val rt: String,
    val rw: String,
    val noTelepon: String,
    val status: String,
    val createdAt: Long
)