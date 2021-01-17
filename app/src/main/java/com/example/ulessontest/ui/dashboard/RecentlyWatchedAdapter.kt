package com.example.ulessontest.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.core.di.room.entities.RecentlyWatched
import com.example.ulessontest.R
import com.example.ulessontest.databinding.RecentlyViewedLayoutItemBinding

class RecentlyWatchedAdapter(val onCLick: (recentlyWatched: RecentlyWatched) -> Unit) : RecyclerView.Adapter<RecentlyWatchedAdapter.ViewHolder>() {

    private var recentlyWatchedVideos: List<RecentlyWatched> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecentlyViewedLayoutItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recently_viewed_layout_item , parent, false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recentlyWatchedVideos[position])
    }

    override fun getItemCount(): Int = recentlyWatchedVideos.size

    fun getRecentlyWatchedVideos(recentlyWatchedVideos: List<RecentlyWatched>) {
        this.recentlyWatchedVideos = recentlyWatchedVideos
        notifyDataSetChanged()
    }

    fun seeLess(recentlyWatchedVideos: List<RecentlyWatched>) {
        this.recentlyWatchedVideos = recentlyWatchedVideos
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RecentlyViewedLayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onCLick.invoke(recentlyWatchedVideos[adapterPosition])
            }
        }

        fun bind(recentlyWatched: RecentlyWatched) {
            when(recentlyWatched.subject.toLowerCase()) {
                Subjects.CHEMISTRY.value -> binding.subject.setTextColor(ContextCompat.getColor(binding.root.context, R.color.chemistry_color))
                Subjects.BIO.value -> binding.subject.setTextColor(ContextCompat.getColor(binding.root.context, R.color.biology_color))
                Subjects.MATHS.value -> binding.subject.setTextColor(ContextCompat.getColor(binding.root.context, R.color.maths_color))
                Subjects.PHY.value -> binding.subject.setTextColor(ContextCompat.getColor(binding.root.context, R.color.physics_color))
                Subjects.ENG.value -> binding.subject.setTextColor(ContextCompat.getColor(binding.root.context, R.color.english_color))
            }
            binding.subject.text = recentlyWatched.subject
            binding.chapter.text = recentlyWatched.chapter
        }
    }
}

enum class Subjects(val value: String) {
    CHEMISTRY("chemistry"), PHY("physics"), ENG("english"), MATHS("mathematics"), BIO("biology")
}