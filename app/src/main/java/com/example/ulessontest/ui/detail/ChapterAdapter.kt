package com.example.ulessontest.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.di.model.Chapter
import com.example.core.di.model.Lesson
import com.example.ulessontest.R
import com.example.ulessontest.databinding.ChaptersLayoutItemBinding

class ChapterAdapter(val onClick: (lesson: Lesson, chapter: String) -> Unit) : RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    private var chapters: List<Chapter> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ChaptersLayoutItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.chapters_layout_item , parent, false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chapters[position])
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    fun getChapters(chapters: List<Chapter>) {
        this.chapters = chapters
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ChaptersLayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var lessonAdapter: LessonAdapter = LessonAdapter {
            onClick.invoke(it, chapters[adapterPosition].name)
        }

        fun bind(chapter: Chapter) {
            binding.title.text = chapter.name

            binding.lessonRecyclerView.setHasFixedSize(true)
            binding.lessonRecyclerView.layoutManager = LinearLayoutManager(binding.title.context, LinearLayoutManager.HORIZONTAL, false)
            binding.lessonRecyclerView.adapter = lessonAdapter
            lessonAdapter.getLessons(chapter.lessons)
        }
    }
}