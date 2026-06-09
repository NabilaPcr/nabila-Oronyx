package com.example.bila_oronyx.Home.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bila_oronyx.R
import com.example.bila_oronyx.data.model.NewsModel
import com.example.bila_oronyx.databinding.ItemNewsBinding
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val items: List<NewsModel>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvTitle.text = item.title ?: "Tanpa Judul"

        val desc = item.description.orEmpty()
        holder.binding.tvDescription.text = if (desc.length > 100) {
            "${desc.take(100)}..."
        } else {
            desc.ifEmpty { "Tidak ada deskripsi." }
        }

        holder.binding.tvAuthor.text = item.author ?: "Unknown"

        holder.binding.tvDate.text = formatDate(item.publishedAt.orEmpty())

        Glide.with(holder.itemView.context)
            .load(item.urlToImage)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_delete)
            .into(holder.binding.imgNews)

        holder.itemView.setOnClickListener {
            if (!item.url.isNullOrEmpty()) {
                val intent = android.content.Intent(holder.itemView.context, com.example.bila_oronyx.BeritaWebActivity::class.java)
                intent.putExtra("url", item.url)
                intent.putExtra("title", item.title)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    private fun formatDate(dateString: String): String {
        if (dateString.isEmpty()) return "-"
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            dateString
        }
    }
}