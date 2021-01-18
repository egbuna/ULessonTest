package com.example.ulessontest.ui.dashboard

import androidx.fragment.app.Fragment
import com.example.ulessontest.R
import com.example.ulessontest.databinding.FragmentDashboardBinding
import com.global.gomoney.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardFragment : Fragment(R.layout.fragment_dashboard) {

    val binding by viewBinding(FragmentDashboardBinding::bind)


}