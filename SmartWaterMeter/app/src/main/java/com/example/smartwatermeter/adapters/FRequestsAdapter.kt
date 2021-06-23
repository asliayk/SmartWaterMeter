package com.example.smartwatermeter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwatermeter.databinding.CardRequestBinding
import com.example.smartwatermeter.fragments.FriendRequestsFragment
import com.example.smartwatermeter.models.RequestModel
import com.example.smartwatermeter.viewmodels.GameViewModel


class FRequestsAdapter(
    private val requestsList: ArrayList<RequestModel>?,
    private var fragment: FriendRequestsFragment
) : ListAdapter<RequestModel, FRequestsAdapter.RowHolder>(FRequestsDiffCallback()) {


    class RowHolder(val binding: CardRequestBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(request: RequestModel, fragment: FriendRequestsFragment) {
            binding.friendName.text = request.nameSurname
            binding.btnYes.setOnClickListener {
                fragment.askNumber(request)
            }
            binding.btnNo.setOnClickListener {
                fragment.rejectReq(request)
            }

        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardRequestBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }


    }


    override fun getItemCount(): Int {

        return requestsList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FRequestsAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        requestsList?.get(position)?.let {
            holder.bind(it,fragment)

        }
    }


}

class FRequestsDiffCallback : DiffUtil.ItemCallback<RequestModel>() {

    override fun areItemsTheSame(oldItem: RequestModel, newItem: RequestModel): Boolean {
        return oldItem.appId == newItem.appId
    }

    override fun areContentsTheSame(oldItem: RequestModel, newItem: RequestModel): Boolean {
        return oldItem == newItem
    }

}