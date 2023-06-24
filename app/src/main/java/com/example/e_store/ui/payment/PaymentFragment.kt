package com.example.e_store.ui.payment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_store.R
import com.example.e_store.databinding.FragmentPaymentBinding
import com.example.e_store.databinding.ProductsFragmentBinding
import com.example.e_store.ui.shopping_cart.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private var binding : FragmentPaymentBinding by autoCleared()
    private val shoppingCartViewModel:ShoppingCartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPaymentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.creditCardNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.creditContainer.helperText = validCredit()
            }
        }
        binding.address.setOnFocusChangeListener { _, focused ->
            if (!focused && binding.address.text!!.isNotEmpty()) {
                binding.addressContainer.helperText = validAddress()
            }
        }
        binding.CVVNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.cvvContainer.helperText = validCVV()
            }
        }
        binding.MonthNumber.setOnFocusChangeListener { _, focused ->
            if (!focused && binding.MonthNumber.text!!.isNotEmpty()) {
                binding.monthContainer.helperText = validMonth()
            }
        }
        binding.YearNumber.setOnFocusChangeListener { _, focused ->
            if (!focused&& binding.YearNumber.text!!.isNotEmpty()) {
                binding.yearContainer.helperText = validYear()
            }
        }
        binding.confirmButton.setOnClickListener {
            if(confirmPurchase()){
                shoppingCartViewModel.deleteAll()
                findNavController().navigate(R.id.action_paymentFragment_to_thankYouFragment,
                    bundleOf("totalPrice" to arguments?.getString("totalPrice") )
                )
            }
            else{
                AlertDialog.Builder(requireContext()).setTitle(getString(R.string.error)).setMessage(getString(
                                    R.string.not_all_the_req)).setPositiveButton(getString(R.string.okey)){ _, _->
                    //do nothing
                }.show()
            }
        }
    }

    private fun validAddress(): CharSequence? {
        val address = binding.address.text.toString()
        if(address.length<2){
            return getString(R.string.invalid_address)

        }
        return null

    }

    private fun confirmPurchase():Boolean {
        val validCredit=binding.creditContainer.helperText==null
        val validCVV=binding.cvvContainer.helperText==null
        val validMonth=binding.monthContainer.helperText==null
        val validYear=binding.yearContainer.helperText==null
        val validAddress=binding.addressContainer.helperText==null
        if(validCredit&&validCVV&&validYear&&validMonth&&validAddress){
            binding.YearNumber.text=null
            binding.creditCardNumber.text=null
            binding.MonthNumber.text=null
            binding.CVVNumber.text=null
            binding.address.text=null
            return true
        }
        return false
    }

    private fun validYear(): CharSequence? {
        val year = binding.YearNumber.text.toString()
        if (year.toInt()  < 23 || year.toInt()  > 32||year==""){
            return getString(R.string.invalid_year)
        }


        return null

    }

    private fun validMonth(): CharSequence? {
        val month = binding.MonthNumber.text.toString()
        if (month.toInt() < 1 || month.toInt()  > 12||month==""){
            return getString(R.string.invalid_month)
        }
        return null
    }




    private fun validCVV(): CharSequence? {
        val CVV = binding.CVVNumber.text.toString()
        if (CVV.length < 3 || CVV.length > 3){
            return getString(R.string.invalid_cvv)
        }
        return null
    }



    private fun validCredit(): CharSequence? {
        val creditCard = binding.creditCardNumber.text.toString()
        if (creditCard.length < 16 || creditCard.length > 16){
            return getString(R.string.invalid_credit_card)
        }


            return null
    }
}