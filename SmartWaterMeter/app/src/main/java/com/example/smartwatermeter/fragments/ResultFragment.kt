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
import com.example.smartwatermeter.databinding.FragmentResultBinding
import com.example.smartwatermeter.viewmodels.LoginViewModel
import com.example.smartwatermeter.viewmodels.ResultViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class ResultFragment : Fragment() {

    private lateinit var resultViewModel: ResultViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentResultBinding>(
            inflater, R.layout.fragment_result,
            container, false
        )
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        val code = args.code
        val aboneNo = args.aboneNo
        val status = args.status
        val verId = args.verId
        resultViewModel = ViewModelProvider(this).get(ResultViewModel::class.java)

        if (status == "opened")
            resultViewModel.publish(
                MainActivity.StaticData.loginData!!.appTopic,
                "aboneNo:" + aboneNo + "\nname:" + MainActivity.StaticData.name + "\ncontrol:O"
            )
        else
            resultViewModel.publish(
                MainActivity.StaticData.loginData!!.appTopic,
                "aboneNo:" + aboneNo + "\nname:" + MainActivity.StaticData.name + "\ncontrol:C"
            )


        resultViewModel.published.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.resImage.setImageResource(R.drawable.ic_check)
                if (status == "opened")
                    binding.resText.text = "You have succesfully opened the meter"
                else
                    binding.resText.text = "You have succesfully closed the meter"
            }
        })

        /*val credential = PhoneAuthProvider.getCredential(verId,code)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                resultViewModel.publish(MainActivity.StaticData.loginData!!.appTopic,
                "aboneNo:12345\nname:ana\ncontrol:C")

                resultViewModel.published.observe(viewLifecycleOwner, Observer {
                    if(it) {
                        binding.resImage.setImageResource(R.drawable.ic_check)
                        binding.resText.text = "Your have succesfully opened the meter"
                    }
                })

            } else {
                binding.resImage.setImageResource(R.drawable.ic_cancel)
                binding.resText.text = "The 6 digit code was entered incorrectly. Please try again."
            }
        } */


        /* if(success) {
             binding.resImage.setImageResource(R.drawable.ic_check)
             binding.resText.text = "Your quota has been successfully set"
         } else {
             binding.resImage.setImageResource(R.drawable.ic_cancel)
             binding.resText.text = "The 4 digit code was entered incorrectly. Please try again."
         } */

        return binding.root
    }


}