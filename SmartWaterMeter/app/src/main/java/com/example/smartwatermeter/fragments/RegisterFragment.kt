package com.example.smartwatermeter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.smartwatermeter.R
import com.example.smartwatermeter.databinding.FragmentMeterOpenCloseBinding
import com.example.smartwatermeter.databinding.FragmentRegisterBinding
import com.example.smartwatermeter.doneAlert
import com.example.smartwatermeter.viewmodels.LoginViewModel
import com.example.smartwatermeter.viewmodels.RegisterViewModel


class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater, R.layout.fragment_register,
            container, false
        )

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener {
           // registerViewModel.register(binding.aboneNo.text.toString().toInt(),binding.mail.text.toString(),
           // binding.password.text.toString(),binding.nameSurname.text.toString())
        }

        registerViewModel.success.observe(viewLifecycleOwner, Observer {
            doneAlert(this, "You have successfully registered!", ::navigateBack)
        })




        return binding.root
    }


    private fun navigateBack() {
        view?.findNavController()?.popBackStack()
    }

}