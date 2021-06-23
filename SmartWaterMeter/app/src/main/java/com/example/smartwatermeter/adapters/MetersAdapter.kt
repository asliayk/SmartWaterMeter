package com.example.smartwatermeter.adapters

import android.content.res.Resources
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwatermeter.R
import com.example.smartwatermeter.databinding.CardControlBinding
import com.example.smartwatermeter.fragments.MeterOpenCloseFragment
import com.example.smartwatermeter.fragments.MeterOpenCloseFragmentDirections
import com.example.smartwatermeter.models.ControlModel


@Suppress("DEPRECATION")
class MetersAdapter(
    private val metersList: ArrayList<ControlModel>?, fragment: MeterOpenCloseFragment
) : ListAdapter<ControlModel, MetersAdapter.RowHolder>(MeterDiffCallback()) {


    val fragment = fragment

    class RowHolder(val binding: CardControlBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(control: ControlModel, fragment: MeterOpenCloseFragment, position: Int) {
            //binding.controlStatus.text = control.status
            binding.controlTitle.text = control.name
            when(position) {
                0-> binding.controlTitle.text = "Kitchen"
                1-> binding.controlTitle.text = "Main Valve"
                2-> binding.controlTitle.text = "Toilet"
                3-> binding.controlTitle.text = "Garden"
            }
            var s = PreferenceManager.getDefaultSharedPreferences(fragment.context)
                .getString("mys", null)
            if (s!![position] == 'O') {
                binding.btnControl.setColorFilter(Color.parseColor("#08C321"))
            } else {
                binding.btnControl.setColorFilter(Color.parseColor("#980404"))
            }


        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardControlBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }


    }


    override fun getItemCount(): Int {

        return metersList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetersAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        metersList?.get(position)?.let {
            holder.bind(it, fragment, position)

            holder?.binding.btnControl.setOnClickListener {
                var s = PreferenceManager.getDefaultSharedPreferences(fragment.context)
                    .getString("mys", null)
                var news = ""
                var x = "closed"
                if (s!![position] == 'O') {
                    println("O yu replace etmiyor mu")
                    news = s.replaceRange(position, position + 1, "C")
                    println(news)
                } else {
                    println("C yi replace etmiyor mu")
                    news = s.replaceRange(position, position + 1, "O")
                    println(news)
                    x = "opened"

                }
                PreferenceManager.getDefaultSharedPreferences(fragment.context).edit()
                    .putString("mys", news)
                    .apply()
                fragment.view?.findNavController()?.navigate(
                    MeterOpenCloseFragmentDirections.actionMeterOpenCloseFragmentToVerificationFragment(
                        metersList?.get(
                            position
                        ).name, x
                    )
                )
            }
        }
    }


}

class MeterDiffCallback : DiffUtil.ItemCallback<ControlModel>() {

    override fun areItemsTheSame(oldItem: ControlModel, newItem: ControlModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ControlModel, newItem: ControlModel): Boolean {
        return oldItem == newItem
    }

}