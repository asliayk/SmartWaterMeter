package com.example.smartwatermeter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.databinding.FragmentSetQuotaBinding
import com.example.smartwatermeter.doneAlert
import com.example.smartwatermeter.fragments.SetQuotaFragmentDirections.Companion.actionSetQuotaFragmentToHomeFragment
import com.example.smartwatermeter.fragments.SetQuotaFragmentDirections.Companion.actionSetQuotaFragmentToResultFragment
import com.example.smartwatermeter.viewmodels.HomeViewModel


class SetQuotaFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSetQuotaBinding>(
            inflater, R.layout.fragment_set_quota,
            container, false
        )

        (activity as MainActivity).setToolbarTitle("Set Quota")
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.btnSetQuota.setOnClickListener {
          var quota = binding.amount.text.toString().toLong()
          println(quota)
          homeViewModel.setQuota(this,quota)
        }

        homeViewModel.navigateBack.observe(viewLifecycleOwner, Observer {
            if(it)
                doneAlert(this, "Your quota has been set successfully!", ::navigateBack)
        })

        return binding.root
    }

    private fun navigateBack() {
        homeViewModel.resetNavigate()
        (activity as MainActivity).setLoading(true)
        view?.findNavController()?.navigate(actionSetQuotaFragmentToHomeFragment())
    }


}