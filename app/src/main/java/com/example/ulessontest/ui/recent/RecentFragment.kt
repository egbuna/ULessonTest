package com.example.ulessontest.ui.recent

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentRecentBinding
import com.example.ulessontest.ui.base.BaseFragment
import com.example.ulessontest.ui.dashboard.DashBoardViewModel
import com.example.ulessontest.ui.dashboard.RecentlyWatchedAdapter
import com.global.gomoney.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment : BaseFragment(R.layout.fragment_recent) {

    private val viewModel: DashBoardViewModel by viewModels()
    private lateinit var recentlyWatchedAdapter: RecentlyWatchedAdapter
    private val binding by viewBinding(FragmentRecentBinding::bind)

    override fun setUp(view: View) {

        binding.exit.setOnClickListener {
            findNavController().popBackStack()
        }

        recentlyWatchedAdapter = RecentlyWatchedAdapter {

        }
        binding.recentlyWatchedRecyclerview.setHasFixedSize(true)
        binding.recentlyWatchedRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recentlyWatchedRecyclerview.adapter = recentlyWatchedAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            val videosWatched = viewModel.getRecentlyWatchedVideos()
            recentlyWatchedAdapter.getRecentlyWatchedVideos(videosWatched)
        }
    }
}