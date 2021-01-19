package com.example.ulessontest.ui.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        private val context by lazy { binding.root.context }

        init {
            binding.root.setOnClickListener {
                onCLick.invoke(subjects[adapterPosition])
            }
        }

        fun onBind(subject: SubjectAndLesson) {
            binding.subject.text = subject.name
            when(subject.name.toLowerCase()) {
                Subjects.PHY.value -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.physics_subject_background))
                    Glide.with(context).load(R.drawable.ic_physics_home_icon).into(binding.icon)
                }
                Subjects.MATHS.value -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.maths_subject_background))
                    Glide.with(context).load(R.drawable.ic_maths_home_icon).into(binding.icon)
                }
                Subjects.ENG.value -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.english_subject_background))
                    Glide.with(context).load(R.drawable.ic_english_home_icon).into(binding.icon)
                }
                Subjects.CHEMISTRY.value -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.chemistry_subject_background))
                    Glide.with(context).load(R.drawable.ic_chemistry_home_icon).into(binding.icon)
                }
                Subjects.BIO.value -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.biology_subject_background))
                    Glide.with(context).load(R.drawable.ic_biology_home_icon).into(binding.icon)
                }
            }
        }
    }
}