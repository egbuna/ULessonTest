package com.example.ulessontest.ui.detail

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.model.MoreInfo
import com.example.core.di.model.SubjectAndLesson
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentSubjectDetailBinding
import com.example.ulessontest.ui.base.BaseFragment
import com.global.gomoney.utils.viewbinding.viewBinding

class SubjectDetailFragment : BaseFragment(R.layout.fragment_subject_detail) {

    private val binding by viewBinding(FragmentSubjectDetailBinding::bind)
    private val args by navArgs<SubjectDetailFragmentArgs>()

    override fun setUp(view: View) {
        binding.title.text = args.subject.name
        binding.exit.setOnClickListener { findNavController().popBackStack() }

        val chapterAdapter = ChapterAdapter {lesson, chapter ->
            findNavController().navigate(SubjectDetailFragmentDirections.actionSubjectDetailFragmentToPlayMediaFragment(lesson, MoreInfo(args.subject.name, chapter)))
        }

        binding.chapterRecyclerView.setHasFixedSize(true)
        binding.chapterRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chapterRecyclerView.adapter = chapterAdapter
        chapterAdapter.getChapters(args.subject.chapters)
    }
}