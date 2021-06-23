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
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.databinding.FragmentLoginBinding
import com.example.smartwatermeter.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToHomeFragment
import com.example.smartwatermeter.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToRegisterFragment
import com.example.smartwatermeter.viewmodels.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login,
            container, false
        )

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        (activity as MainActivity).setVisibilities(false)

        binding.btnLogin.setOnClickListener {
            if (binding.mail.text.toString().isEmpty())
                binding.mail.error = "This field cannot be empty"

            if (binding.mail.text.toString().isEmpty())
                binding.password.error = "This field cannot be empty"

            if(binding.mail.text.toString().isNotEmpty() && binding.password.text.toString().isNotEmpty()) {
                (activity as MainActivity).setLoading(true)
                loginViewModel.login(binding.mail.text.toString(),binding.password.text.toString())
            }


        }

        loginViewModel.loginRes.observe(viewLifecycleOwner, Observer {
            MainActivity.StaticData.loginData = it
            loginViewModel.connectMqtt(requireActivity() as MainActivity,requireContext(),it)

        })

        loginViewModel.mqttConnect.observe(viewLifecycleOwner, Observer {
            if(it) {
                loginViewModel.subscribe(MainActivity.StaticData.loginData!!.appCont_topic)
                loginViewModel.subscribe(MainActivity.StaticData.loginData!!.appTopic)
            }
        })

        loginViewModel.subscribed.observe(viewLifecycleOwner, Observer {
            if(it)
                view?.findNavController()?.navigate(actionLoginFragmentToHomeFragment())
        })

        binding.btnSignup.setOnClickListener {
            view?.findNavController()?.navigate(actionLoginFragmentToRegisterFragment())
        }



            return binding.root
        }


    }