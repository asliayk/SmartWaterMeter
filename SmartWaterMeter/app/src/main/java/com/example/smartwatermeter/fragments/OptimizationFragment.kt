package com.example.smartwatermeter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.adapters.SectionPagerAdapter
import com.example.smartwatermeter.databinding.FragmentHomeBinding
import com.example.smartwatermeter.databinding.FragmentOptimizationBinding
import com.google.android.material.tabs.TabLayout


class OptimizationFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentOptimizationBinding>(inflater,R.layout.fragment_optimization,
            container,false)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        (activity as MainActivity).setToolbarTitle("Optimization")
        (activity as MainActivity).setAddFriendButtonVisibility(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupTabs()
    }

    private fun setupTabs() {
        val adapter = SectionPagerAdapter(childFragmentManager)
        adapter.addFragment(FriendsFragment(),"Friends")
        adapter.addFragment(FriendRequestsFragment(),"Requests")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).setAddFriendButtonVisibility(false)
    }
}