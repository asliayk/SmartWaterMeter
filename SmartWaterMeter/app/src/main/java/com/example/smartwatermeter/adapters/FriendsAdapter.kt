package com.example.smartwatermeter.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.databinding.CardFriendBinding
import com.example.smartwatermeter.fragments.FriendsFragment
import com.example.smartwatermeter.models.FriendModel


class FriendsAdapter(
    private val friendsList: ArrayList<FriendModel>?,
    private val fragment: FriendsFragment
) : ListAdapter<FriendModel, FriendsAdapter.RowHolder>(FriendsDiffCallback()) {


    class RowHolder(val binding: CardFriendBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(friend: FriendModel, fragment: FriendsFragment) {
            if(MainActivity.StaticData.loginData?.appId==friend.appId) {
                binding.friendName.text = "You"
                binding.friendName.setTextColor(ContextCompat.getColor(fragment.requireContext(), R.color.appRed))
                binding.friendName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19F)
                binding.btnDelete.visibility = View.GONE
            } else {
                binding.btnDelete.visibility = View.VISIBLE
                binding.friendName.text = (friend.index+1).toString() + ") " + friend.nameSurname
                binding.friendName.setTextColor(ContextCompat.getColor(fragment.requireContext(), R.color.black))
            }
            binding.dUsage.text = friend.dusage.toString()
            binding.mUsage.text = friend.musage.toString()

            binding.btnDelete.setOnClickListener {
                fragment.deleteFriend(friend.appId)
            }
        }

        companion object {
            fun from(parent: ViewGroup): RowHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardFriendBinding.inflate(layoutInflater, parent, false)
                return RowHolder(binding)
            }
        }


    }


    override fun getItemCount(): Int {

        return friendsList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAdapter.RowHolder {
        return RowHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        friendsList?.get(position)?.let {
            holder.bind(it,fragment)

        }
    }


}

class FriendsDiffCallback : DiffUtil.ItemCallback<FriendModel>() {

    override fun areItemsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean {
        return oldItem.appId == newItem.appId
    }

    override fun areContentsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean {
        return oldItem == newItem
    }

}