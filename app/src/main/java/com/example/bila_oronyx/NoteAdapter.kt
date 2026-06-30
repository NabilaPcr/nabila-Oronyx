package com.example.bila_oronyx

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bila_oronyx.data.entity.NoteEntity
import com.example.bila_oronyx.databinding.ItemNoteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteAdapter(
    private val notes: List<NoteEntity>,
    private val noteFragment: FragmentNote
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        holder.binding.apply {
            tvTitle.text = note.title
            tvContent.text = note.content

            btnDelete.setOnClickListener {
                MaterialAlertDialogBuilder(holder.itemView.context)
                    .setTitle("Hapus Catatan")
                    .setMessage("Apakah kamu yakin ingin menghapus catatan ini?")
                    .setPositiveButton("Ya") { dialog, _ ->
                        noteFragment.deleteNote(note)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Batal") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = notes.size
}