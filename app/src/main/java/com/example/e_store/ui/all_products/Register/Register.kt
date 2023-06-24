package com.example.e_store.ui.Register


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_store.R
import com.example.e_store.databinding.RegisterBinding
import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.Repository.FirebaseImpl.AuthRepositoryFirebase
import com.google.firebase.auth.FirebaseAuth

import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared


class Register: Fragment() {
    var auth = FirebaseAuth.getInstance()
    var binding: RegisterBinding by autoCleared()
    private val viewModel: RegisterViewModel by viewModels() {
        RegisterViewModel.RegisterViewModelFactory(AuthRepositoryFirebase())
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = RegisterBinding.inflate(inflater, container, false)
        binding.userRegisterButton.setOnClickListener {
            viewModel.createUser(
                binding.edxtUserName.editText?.text.toString(),
                binding.edxtEmailAddress.editText?.text.toString(),
                binding.edxtPhoneNum.editText?.text.toString(),
                binding.edxtPassword.editText?.text.toString()
            )
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel.userRegisterationStatus.observe(viewLifecycleOwner) {
            when (it) {
                is FirebaseResource.Loading -> {
                    binding.registerProgress.isVisible = true
                    binding.userRegisterButton.isEnabled = false
                }
                is FirebaseResource.Success -> {
                    if(binding.edxtUserName.editText?.text.toString().isEmpty()&&
                        binding.edxtPassword.editText?.text.toString().isEmpty())
                    {
                        Toast.makeText(requireContext(),
                            getString(R.string.username)+" = ${auth.currentUser?.displayName.toString()}\n" +
                                    "@string/email = ${auth.currentUser?.email.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else
                    {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.username)+"= ${binding.edxtUserName.editText?.text.toString()}\n" +
                                    "@string/password"+" = ${binding.edxtPassword.editText?.text.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    findNavController().navigate(R.id.action_register2_to_loginScreen)

                }
                is FirebaseResource.Error -> {
                    binding.registerProgress.isVisible = true
                    binding.userRegisterButton.isEnabled = false
                }
            }
        }

    }
}