package com.example.smartwatermeter.fragments

import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.smartwatermeter.fragments.HomeFragmentDirections.Companion.actionHomeFragmentToSetQuotaFragment
import com.example.smartwatermeter.viewmodels.HomeViewModel
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {


    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setLoading(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home,
            container, false
        )

        FirebaseAuth.getInstance().signOut()
        (activity as MainActivity).setToolbarTitle("Home")
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        (activity as MainActivity).setVisibilities(true)
        (activity as MainActivity).setLogoutButtonVisibility(true)



        homeViewModel.getQuota()
        homeViewModel.getName()


        //handleWatermeter(MainActivity.StaticData.loginData?.appTopic!!,)
        //subscribe(MainActivity.StaticData.loginData?.appCont_topic!!)


        homeViewModel.name.observe(viewLifecycleOwner, Observer {
            MainActivity.StaticData.name = it
        })



        homeViewModel.quota.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).setLoading(false)
            val usages = ArrayList<PieEntry>()
            var pieChart = binding.pieChart

            var newVal = (2800 / it).toFloat()
            usages.add(PieEntry(newVal, "Used"))
            usages.add(PieEntry(100.0f - newVal, "Unused"))

            //usages.add(PieEntry(35.0f, "Used"))
            //usages.add(PieEntry(65.0f, "Unused"))

            val dataSet = PieDataSet(usages, "")

            dataSet.setDrawIcons(false)
            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0F, 40F)
            dataSet.selectionShift = 5f
            var clist = arrayListOf<Int>()
            clist.add(Color.BLUE)
            clist.add(Color.GRAY)
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
            //pieChart.centerText = "28 / 80 TL"
            pieChart.setCenterTextSize(17.0f)
            pieChart.setEntryLabelTextSize(15f)
            pieChart.description.text = ""
            pieChart.legend.isEnabled = false


            println("quotaaa")
            println(it)
            pieChart.centerText = "28 / $it TL"

        })

        binding.btnQuota.setOnClickListener {
            view?.findNavController()?.navigate(actionHomeFragmentToSetQuotaFragment())
        }




        return binding.root
    }


    override fun onStop() {
        super.onStop()
        (activity as MainActivity).setLogoutButtonVisibility(false)
    }
}