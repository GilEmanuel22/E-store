package com.example.e_store.ui.shopping_cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import kotlin.math.round
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_store.R
import com.example.e_store.data.models.CartItem
import com.example.e_store.databinding.CartItemBinding

import com.example.e_store.databinding.ShoppingCartFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class ShoppingCartFragment:Fragment(),ShoppingCartAdapter.ItemListener {
    private var binding: ShoppingCartFragmentBinding by autoCleared()
    private val viewModel: ShoppingCartViewModel by viewModels()
    private lateinit var adapter:ShoppingCartAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ShoppingCartFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= ShoppingCartAdapter(this)
        binding.shoppingCartRv.adapter=adapter
        binding.shoppingCartRv.layoutManager=LinearLayoutManager(requireContext())


        viewModel.items?.observe(viewLifecycleOwner){
            adapter.setProducts(ArrayList(it))
            binding.totalprice.text=calc(it)
        }
        ItemTouchHelper(object :ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            )= makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("NO NEED")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item=(binding.shoppingCartRv.adapter as ShoppingCartAdapter).itemAt(viewHolder.adapterPosition)
                val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                builder.setTitle(getString(R.string.confirm_delete)).setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_item)).setPositiveButton(getString(
                                    R.string.yes)) { p0, p1 -> viewModel.deleteItem(item)
                    Toast.makeText(requireContext(),getString(R.string.item_deleted),Toast.LENGTH_SHORT).show()}.setNegativeButton(getString(
                                        R.string.no)){ p0, p1->viewModel.items?.observe(viewLifecycleOwner){
                    adapter.setProducts(ArrayList(it))
                    binding.totalprice.text=calc(it)
                }}.show()
            }
        }).attachToRecyclerView(binding.shoppingCartRv)

        binding.checkoutbutton.setOnClickListener {
            if(binding.totalprice.text.toString()!= "0.0"){
                findNavController().navigate(R.id.action_shoppingCartFragment2_to_paymentFragment,
                    bundleOf("totalPrice" to binding.totalprice.text.toString())
                )
            }
            else{
                Toast.makeText(requireContext(),getString(R.string.no_item_in_cart),Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onItemClicked(index: Int) {
        findNavController().navigate(
            R.id.action_shoppingCartFragment2_to_singleProductFragment,
            bundleOf("id" to index)
        )
    }

    fun calc(list:List<CartItem>):String{
        var sum:Float=0.0F
        if(list.isNotEmpty()){
            for(item in list){

                sum+= item.price.toFloat()*item.quantity.toFloat()
            }
        }
        return (round(sum*100)/100).toString()
    }

}