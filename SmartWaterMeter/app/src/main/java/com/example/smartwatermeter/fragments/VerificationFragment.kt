package com.example.smartwatermeter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.databinding.FragmentMeterOpenCloseBinding
import com.example.smartwatermeter.databinding.FragmentVerificationBinding
import com.example.smartwatermeter.fragments.VerificationFragmentDirections.Companion.actionVerificationFragmentToResultFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION")
class VerificationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentVerificationBinding>(
            inflater, R.layout.fragment_verification,
            container, false
        )

        (activity as MainActivity).setToolbarTitle("Verification")

        val args = VerificationFragmentArgs.fromBundle(requireArguments())
        val aboneNo = args.aboneNo
        val status = args.status

        binding.btnVerify.setOnClickListener {
            view?.findNavController()?.navigate(actionVerificationFragmentToResultFragment(binding.otpText.text.toString(),aboneNo,status,"0"))
        }


        /*PhoneAuthProvider.getInstance().verifyPhoneNumber("+905******",60,TimeUnit.SECONDS,requireActivity(),
        object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                println("verification completed")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                println("verification failed")
                println(e.message)
            }

            override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                binding.btnVerify.setOnClickListener {
                    view?.findNavController()?.navigate(actionVerificationFragmentToResultFragment(binding.otpText.text.toString(),aboneNo,status,verificationId))
                }

            }
        })*/


        // Inflate the layout for this fragment
        return binding.root
    }


}