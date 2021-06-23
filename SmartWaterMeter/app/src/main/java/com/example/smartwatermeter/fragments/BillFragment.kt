package com.example.smartwatermeter.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.databinding.FragmentBillBinding
import com.example.smartwatermeter.databinding.FragmentOptimizationBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF


class BillFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBillBinding>(inflater,R.layout.fragment_bill,
            container,false)

        val args = BillFragmentArgs.fromBundle(requireArguments())
        var bill = args.bill
        (activity as MainActivity).setToolbarTitle("Bill")

        val usages = ArrayList<PieEntry>()
        var pieChart = binding.pieChart

        binding.titleTime.text = "March 2021"

        usages.add(PieEntry(72.0f, "Kitchen"))
        usages.add(PieEntry(20.0f, "Bathroom"))
        usages.add(PieEntry(8.0f, "Toilet"))

        val dataSet = PieDataSet(usages, "")

        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f
        var clist = arrayListOf<Int>()
        clist.add(Color.BLUE)
        clist.add(Color.GRAY)
        clist.add(Color.DKGRAY)
        dataSet.colors = clist

        val data = PieData(dataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.invalidate()
        pieChart.animateXY(1000, 1000)
        pieChart.isRotationEnabled = false
        pieChart.transparentCircleRadius = 0f
        pieChart.centerText = "72.0 / 100"
        pieChart.setCenterTextSize(17.0f)
        pieChart.setEntryLabelTextSize(15f)
        pieChart.description.text=""
        pieChart.legend.isEnabled=false

        return binding.root
    }


}