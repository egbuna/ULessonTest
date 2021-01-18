package com.example.ulessontest.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.example.core.di.model.Lesson
import com.example.ulessontest.R
import com.example.ulessontest.databinding.LessonLayoutItemBinding

class LessonAdapter(val onCLick: (lesson: Lesson) -> Unit) : RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    private var lessons: List<Lesson> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LessonLayoutItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.lesson_layout_item , parent, false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lessons[position])
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun getLessons(lessons: List<Lesson>) {
        this.lessons = lessons
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LessonLayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onCLick.invoke(lessons[adapterPosition])
            }
        }
        fun bind(lesson: Lesson) {
            binding.lessonTitle.text = lesson.name
            Glide.with(binding.lessonImage.context).load(lesson.icon).transform(CenterInside()).into(binding.lessonImage)
        }
    }
}