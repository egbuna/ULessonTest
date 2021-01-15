package com.example.ulessontest.ui.dashboard

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.core.di.network.NetworkStatus
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentDashboardBinding
import com.example.ulessontest.ui.base.BaseFragment
import com.global.gomoney.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : BaseFragment(R.layout.fragment_dashboard) {

    val viewModel: DashBoardViewModel by viewModels()
    val binding by viewBinding(FragmentDashboardBinding::bind)

    override fun setUp(view: View) {
        setUpObserver()
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
                    Log.e("NETWORK", "Success data ${it.data}")
                }
            }
        })
    }
}