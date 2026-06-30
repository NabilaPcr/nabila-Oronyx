package com.example.bila_oronyx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bila_oronyx.data.AppDatabase
import com.example.bila_oronyx.data.entity.NoteEntity
import com.example.bila_oronyx.databinding.FragmentNoteBinding
import com.example.bila_oronyx.utils.NotificationHelper
import kotlinx.coroutines.launch

class FragmentNote : Fragment(R.layout.fragment_note) {  // 🔥 Pakai fragment_note.xml

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: NoteAdapter
    private lateinit var notificationHelper: NotificationHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNoteBinding.bind(view)

        notificationHelper = NotificationHelper(requireContext())
        db = AppDatabase.getInstance(requireContext())

        // ✅ rvNotes ada di layout fragment_note.xml
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvNotes.addItemDecoration(divider)

        // ✅ fabAddNote ada di layout fragment_note.xml
        binding.fabAddNote.setOnClickListener {
            startActivity(android.content.Intent(requireContext(), NoteFormActivity::class.java))
        }

        fetchNotes()
    }

    private fun fetchNotes() {
        lifecycleScope.launch {
            val notes = db.noteDao().getAll()
            adapter = NoteAdapter(notes, this@FragmentNote)
            binding.rvNotes.adapter = adapter
        }
    }

    fun deleteNote(note: NoteEntity) {
        lifecycleScope.launch {
            db.noteDao().delete(note)
            notificationHelper.showNotification(
                title = "Catatan Dihapus 🗑️",
                message = "Catatan '${note.title}' telah dihapus."
            )
            fetchNotes()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}