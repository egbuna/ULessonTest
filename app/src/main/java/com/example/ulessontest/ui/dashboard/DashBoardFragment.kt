package com.example.ulessontest.ui.dashboard

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.model.Lesson
import com.example.core.di.model.MoreInfo
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentDashboardBinding
import com.example.ulessontest.ui.base.BaseFragment
import com.global.gomoney.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel: DashBoardViewModel by viewModels()
    private val binding by viewBinding(FragmentDashboardBinding::bind)

    private lateinit var subjectAdapter: SubjectAdapter
    private lateinit var recentlyWatchedAdapter: RecentlyWatchedAdapter

    override fun setUp(view: View) {
        setUpObserver()
        subjectAdapter = SubjectAdapter {
            findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToSubjectDetailFragment(it))
        }
        binding.subjectsRecyclerview.setHasFixedSize(true)
        binding.subjectsRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.subjectsRecyclerview.adapter = subjectAdapter

        recentlyWatchedAdapter = RecentlyWatchedAdapter {
            val lesson = Lesson("", it.lesson, it.icon, it.mediaUrl, 0, 0)
            val moreInfo = MoreInfo(it.subject, it.chapter)
            findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToPlayMediaFragment(lesson, moreInfo))
        }

        binding.viewAllBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_recentFragment)
        }

        binding.recentlyWatchedRecyclerview.setHasFixedSize(true)
        binding.recentlyWatchedRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recentlyWatchedRecyclerview.adapter = recentlyWatchedAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            val videosWatched =  viewModel.getRecentlyWatchedVideos()
            if (videosWatched.isNotEmpty()) {
                binding.recentTitle.visibility = View.VISIBLE
                if (videosWatched.size > 2) {
                    recentlyWatchedAdapter.getRecentlyWatchedVideos(videosWatched.subList(0, 2))
                    binding.container.visibility = View.VISIBLE
                } else {
                    recentlyWatchedAdapter.getRecentlyWatchedVideos(videosWatched)
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel.getSubjectLiveData.observe(viewLifecycleOwner, {
            subjectAdapter.addSubjects(it)
            binding.subjectShimmer.shimmerRoot.visibility = View.GONE
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.root)
            constraintSet.connect(R.id.recent_title, ConstraintSet.TOP, R.id.subjects_recyclerview, ConstraintSet.BOTTOM, 25)
            constraintSet.applyTo(binding.root)
        })
    }
}