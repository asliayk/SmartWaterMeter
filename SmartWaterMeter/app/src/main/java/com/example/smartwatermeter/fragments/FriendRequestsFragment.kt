package com.example.smartwatermeter.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartwatermeter.R
import com.example.smartwatermeter.adapters.FRequestsAdapter
import com.example.smartwatermeter.adapters.FriendsAdapter
import com.example.smartwatermeter.databinding.FragmentFriendRequestsBinding
import com.example.smartwatermeter.doneAlert
import com.example.smartwatermeter.hideKeyboard
import com.example.smartwatermeter.models.RequestModel
import com.example.smartwatermeter.viewmodels.GameViewModel
import com.google.android.material.textfield.TextInputEditText


class FriendRequestsFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentFriendRequestsBinding>(inflater,R.layout.fragment_friend_requests,
            container,false)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        val recView = binding.requestsList
        viewModel.getRequests()


        viewModel.requests.observe(viewLifecycleOwner, Observer {
            var list = it as ArrayList<RequestModel>
            list.add(RequestModel("xx97","xx97"))
            val fAdapter = FRequestsAdapter(list,this)
            recView.adapter = fAdapter
            recView.setHasFixedSize(true)
        })

        viewModel.addF.observe(viewLifecycleOwner, Observer {
          doneAlert(this, "You and $it are now friends!",null)
        })


        return binding.root
    }

    fun askNumber(requestModel: RequestModel) {
        var dialog = AlertDialog.Builder(context,R.style.MaterialAlertDialog_color)
        var dialogView = layoutInflater.inflate(R.layout.custom_dialog,null)
        var edit = dialogView.findViewById<TextInputEditText>(R.id.name)
        dialog.setView(dialogView)
        dialog.setCancelable(true)
        dialog.setTitle("Pick A Number")
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int -> }
        dialog.setPositiveButton("Create") { _: DialogInterface, _: Int ->
            hideKeyboard(requireActivity())
            var s = "friends"+edit.text.toString()
            viewModel.acceptRequest(requestModel, s)
        }
        dialog.show()
    }

    fun rejectReq(requestModel: RequestModel) {
        viewModel.rejectRequest(requestModel)
    }

}