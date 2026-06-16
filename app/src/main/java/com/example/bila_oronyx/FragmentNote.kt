package com.example.bila_oronyx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bila_oronyx.data.AppDatabase
import com.example.bila_oronyx.data.entity.NoteEntity
import com.example.bila_oronyx.databinding.FragmentNoteBinding
import kotlinx.coroutines.launch


class FragmentNote : Fragment(R.layout.fragment_note) {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNoteBinding.bind(view)

        // Inisialisasi Database
        db = AppDatabase.getInstance(requireContext())

        // Setup RecyclerView
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())

        // Tambahkan garis pemisah antar item
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvNotes.addItemDecoration(divider)

        // Tombol FAB untuk tambah catatan
        binding.fabAddNote.setOnClickListener {
            startActivity(android.content.Intent(requireContext(), NoteFormActivity::class.java))
        }

        // Fetch data awal
        fetchNotes()
    }

    // Fungsi untuk mengambil data dari database
    private fun fetchNotes() {
        lifecycleScope.launch {
            val notes = db.noteDao().getAll()
            adapter = NoteAdapter(notes, this@FragmentNote)
            binding.rvNotes.adapter = adapter
        }
    }

    // Fungsi untuk menghapus catatan
    fun deleteNote(note: NoteEntity) {
        lifecycleScope.launch {
            db.noteDao().delete(note)
            fetchNotes() // Refresh data setelah delete
        }
    }

    // Refresh data saat kembali ke fragment
    override fun onResume() {
        super.onResume()
        fetchNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}