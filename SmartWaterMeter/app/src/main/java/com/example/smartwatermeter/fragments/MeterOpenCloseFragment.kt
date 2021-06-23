package com.example.smartwatermeter.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.adapters.MetersAdapter
import com.example.smartwatermeter.databinding.FragmentMeterOpenCloseBinding
import com.example.smartwatermeter.models.ControlModel
import com.example.smartwatermeter.viewmodels.OpenCloseViewModel


@Suppress("DEPRECATION")
class MeterOpenCloseFragment : Fragment() {

    private lateinit var oviewModel: OpenCloseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMeterOpenCloseBinding>(
            inflater, R.layout.fragment_meter_open_close,
            container, false
        )

        (activity as MainActivity).setToolbarTitle("Power")

        /*binding.btnPower.setOnClickListener {
            view?.findNavController()?.navigate(actionMeterOpenCloseFragmentToVerificationFragment())
        } */

        var s = PreferenceManager.getDefaultSharedPreferences(context).getString("mys", null)
        println(s)


        oviewModel = ViewModelProvider(this).get(OpenCloseViewModel::class.java)
        val recView = binding.meterList
        oviewModel.getControl()

        oviewModel.controlList.observe(viewLifecycleOwner, Observer {
            println(it.toString())
            if(PreferenceManager.getDefaultSharedPreferences(context).getString("id", null)!=MainActivity.StaticData.loginData!!.appId ||  PreferenceManager.getDefaultSharedPreferences(context).getString("mys", null)==null) {
                var s = ""
                for (i in it)
                    s += "O"

                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("mys", s)
                    .apply()
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("id", MainActivity.StaticData.loginData!!.appId)
                    .apply()
            }

            val metersAdapter = MetersAdapter(it as ArrayList<ControlModel>?,this)
            recView.adapter = metersAdapter
            recView.setHasFixedSize(true)
        })

        return binding.root
    }


}