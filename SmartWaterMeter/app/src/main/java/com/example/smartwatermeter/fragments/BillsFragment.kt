package com.example.smartwatermeter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.adapters.BillsAdapter
import com.example.smartwatermeter.databinding.FragmentBillsBinding
import com.example.smartwatermeter.models.BillModel
import com.example.smartwatermeter.viewmodels.BillViewModel
import com.example.smartwatermeter.viewmodels.HomeViewModel


class BillsFragment : Fragment() {

    private lateinit var billViewModel: BillViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBillsBinding>(inflater,R.layout.fragment_bills,
            container,false)

        (activity as MainActivity).setToolbarTitle("Bills")

        val recView = binding.billList
        val bills = arrayListOf<BillModel>()
        billViewModel = ViewModelProvider(this).get(BillViewModel::class.java)
       // billViewModel.getBill()

       // billViewModel.bill.observe(viewLifecycleOwner, Observer {
            bills.add(BillModel(0,null,null,null,null))
            bills.add(BillModel(1,null,null,null,null))
            bills.add(BillModel(2,null,null,null,null))
            bills.add(BillModel(3,null,null,null,null))
            bills.add(BillModel(4,null,null,null,null))
            val billsAdapter = BillsAdapter(bills,this)
            recView.adapter = billsAdapter
            recView.setHasFixedSize(true)

        /*})


        bills.add(BillModel(1,"January 2021","85 TL"))
        bills.add(BillModel(2,"December 2020","80 TL"))
        bills.add(BillModel(3,"November 2020","85 TL"))
        bills.add(BillModel(4,"October 2020","80 TL"))
        bills.add(BillModel(5,"September 2020","85 TL"))
        bills.add(BillModel(6,"August 2020","100 TL"))
        val billsAdapter = BillsAdapter(bills,this)
        recView.adapter = billsAdapter
        recView.setHasFixedSize(true)*/

       // })


        return binding.root
    }


}