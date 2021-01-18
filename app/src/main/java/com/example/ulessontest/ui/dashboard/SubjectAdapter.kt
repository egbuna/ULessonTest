package com.example.ulessontest.ui.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.di.model.SubjectAndLesson
import com.example.ulessontest.R
import com.example.ulessontest.databinding.SubjectsLayoutItemBinding
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.subjects_layout_item.view.*

class SubjectAdapter(val onCLick: (subject: SubjectAndLesson) -> Unit) : RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    private var subjects: List<SubjectAndLesson> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SubjectsLayoutItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
            R.layout.subjects_layout_item , parent, false)))
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(subject = subjects[position])
    }

    fun addSubjects(subjects: List<SubjectAndLesson>) {
        this.subjects = subjects
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: SubjectsLayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onCLick.invoke(subjects[adapterPosition])
            }
        }

        fun onBind(subject: SubjectAndLesson) {
            when(subject.name) {
                "Physics" -> Glide.with(binding.subjectView.context).load(R.drawable.physics).into(binding.subjectView)
                "Mathematics" -> Glide.with(binding.subjectView.context).load(R.drawable.mathematics).into(binding.subjectView)
                "English" -> Glide.with(binding.subjectView.context).load(R.drawable.english).into(binding.subjectView)
                "Chemistry" -> Glide.with(binding.subjectView.context).load(R.drawable.chemistry).into(binding.subjectView)
                "Biology" -> Glide.with(binding.subjectView.context).load(R.drawable.biology).into(binding.subjectView)
                else -> Glide.with(binding.subjectView.context).load(R.drawable.biology).into(binding.subjectView)
            }
        }
    }
}