package com.example.bila_oronyx.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bila_oronyx.data.entity.WargaEntity

@Dao
interface WargaDao {
    @Query("SELECT * FROM warga ORDER BY nama ASC")
    suspend fun getAll(): List<WargaEntity>

    @Insert
    suspend fun insert(warga: WargaEntity)

    @Delete
    suspend fun delete(warga: WargaEntity)
}