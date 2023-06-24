package com.example.e_store.ui.Login

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
import com.example.e_store.databinding.LoginScreenLayoutBinding
import com.example.e_store.utils.FirebaseResource
import com.example.tiuta.Repository.FirebaseImpl.AuthRepositoryFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared



class LoginScreen: Fragment() {
    lateinit var auth : FirebaseAuth

    private var binding: LoginScreenLayoutBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModel.LoginViewModelFactory(AuthRepositoryFirebase())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginScreenLayoutBinding.inflate(inflater, container, false)
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreen_to_register2)
        }
        binding.loginBtn.setOnClickListener {
            viewModel.signInUser(binding.editTextLoginEmail.editText?.text.toString(),
                binding.editTextLoginPass.editText?.text.toString()
            )
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userSignInStatus.observe(viewLifecycleOwner){
            when(it){
                is FirebaseResource.Loading->{
                    binding.loginProgressBar.isVisible = true
                    binding.loginBtn.isEnabled = false
                }
                is FirebaseResource.Success->{
                    Toast.makeText(requireContext(),getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginScreen_to_allProductsFragment)
                }
                is FirebaseResource.Error->{
                    binding.loginProgressBar.isVisible = false
                    binding.loginBtn.isEnabled = true
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner){
            when(it){
                is FirebaseResource.Loading->{
                    binding.loginProgressBar.isVisible = true
                    binding.loginBtn.isEnabled = false
                }
                is FirebaseResource.Success->{
                    findNavController().navigate(R.id.action_loginScreen_to_allProductsFragment)
                }
                is FirebaseResource.Error->{
                    binding.loginProgressBar.isVisible = false
                    binding.loginBtn.isEnabled = true
                }
            }
        }
    }
}


