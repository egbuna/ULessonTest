package com.example.ulessontest.ui.dashboard

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.network.NetworkStatus
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

    override fun setUp(view: View) {
        setUpObserver()
        subjectAdapter = SubjectAdapter {

        }
        binding.subjectsRecyclerview.setHasFixedSize(true)
        binding.subjectsRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.subjectsRecyclerview.adapter = subjectAdapter
    }

    private fun setUpObserver() {
        viewModel.getSubjectLiveData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is NetworkStatus.Loading -> {
                    Log.e("NETWORK", "Loading")
                }
                is NetworkStatus.Error -> {
                    Log.e("NETWORK", "Error ${it.message}")
                }
                is NetworkStatus.Success -> {
                    subjectAdapter.addSubjects(it.data!!.subjects)
                    binding.subjectShimmer.shimmerRoot.visibility = View.GONE
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(binding.root)
                    constraintSet.connect(R.id.recent_title, ConstraintSet.TOP, R.id.subjects_recyclerview, ConstraintSet.BOTTOM, 25)
                    constraintSet.applyTo(binding.root)
                }
            }
        })
    }
}