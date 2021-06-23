package com.example.smartwatermeter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwatermeter.databinding.CardBillBinding
import com.example.smartwatermeter.fragments.BillsFragment
import com.example.smartwatermeter.fragments.BillsFragmentDirections.Companion.actionBillsFragmentToBillFragment
import com.example.smartwatermeter.models.BillModel


class BillsAdapter(
    private val billsList: ArrayList<BillModel>?, fragment: BillsFragment
) : ListAdapter<BillModel, BillsAdapter.RowHolder>(BillDiffCallback()) {


    val fragment = fragment

    class RowHolder(val binding: CardBillBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bill: BillModel) {
            when (bill.aboneNo_0) {
                0 -> {
                    binding.billTitle.text = "March 2021"
                    binding.billAmount.text = "" + "15 m3"
                    binding.billPay.text = "" + 74 + " TL"
                    binding.btnPay.visibility = View.VISIBLE
                }
                1 -> {
                    binding.billTitle.text = "February 2021"
                    binding.billAmount.text = "" + "15.6 m3"
                    binding.billPay.text = "" + 71 + " TL"
                    binding.btnPay.visibility = View.GONE
                }
                2 -> {
                    binding.billTitle.text = "January 2021"
                    binding.billAmount.text = "" + "13.3 m3"
                    binding.billPay.text = "" + 66 + " TL"
                    binding.btnPay.visibility = View.GONE
                }
                3 -> {
                    binding.billTitle.text = "December 2020"
                    binding.billAmount.text = "" + "16.6 m3"
                    binding.billPay.text = "" + 82 + " TL"
                    binding.btnPay.visibility = View.GONE
                }
                4 -> {
                    binding.billTitle.text = "November 2020"
                    binding.billAmount.text = "" + "15.5 m3"
                    binding.billPay.text = "" + 70 + " TL"
                    binding.btnPay.visibility = View.GONE
                }

            }

        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardBillBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }


    }


    override fun getItemCount(): Int {

        return billsList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillsAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        billsList?.get(position)?.let {
            holder.bind(it)
            holder?.itemView!!.setOnClickListener {
                fragment.findNavController()?.navigate(
                    actionBillsFragmentToBillFragment(
                        billsList?.get(position)!!
                    )
                )
            }
            holder?.binding.btnPay.setOnClickListener {
                billsList.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }


}

class BillDiffCallback : DiffUtil.ItemCallback<BillModel>() {

    override fun areItemsTheSame(oldItem: BillModel, newItem: BillModel): Boolean {
        return oldItem.aboneNo_0 == newItem.aboneNo_0
    }

    override fun areContentsTheSame(oldItem: BillModel, newItem: BillModel): Boolean {
        return oldItem == newItem
    }

}