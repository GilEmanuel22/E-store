package com.example.e_store.ui.thank_you_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.e_store.R
import com.example.e_store.databinding.FragmentThankYouBinding
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class ThankYouFragment : Fragment() {
    private var binding:FragmentThankYouBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.grandTotal.text = arguments?.getString("totalPrice")
        binding.continueShoppingButton.setOnClickListener {
            findNavController().navigate(R.id.action_thankYouFragment_to_allProductsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentThankYouBinding.inflate(inflater,container,false)
        return binding.root
    }

}