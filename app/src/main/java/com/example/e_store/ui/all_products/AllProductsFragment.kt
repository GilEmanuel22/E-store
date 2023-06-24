package com.example.e_store.ui.all_products

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_store.R
import com.example.e_store.databinding.ProductsFragmentBinding
import com.example.tiuta.Repository.FirebaseImpl.AuthRepositoryFirebase
import com.example.tiuta.Repository.FirebaseImpl.ProductRepositoryFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class AllProductsFragment : Fragment(),ProductsAdapter.ProductItemListener {
    lateinit var auth: FirebaseAuth
    private var binding:ProductsFragmentBinding by autoCleared()
    private val viewModel:AllProductsViewModel by viewModels()
    private val firebaseViewModel : AllProductsFirebaseViewModel by viewModels {
        AllProductsFirebaseViewModel.ProductViewModelFactory(
            AuthRepositoryFirebase(),
            ProductRepositoryFirebase()
        )
    }
    private lateinit var adapter:ProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= ProductsAdapter(this)
        binding.productsRv.layoutManager= LinearLayoutManager(requireContext())
        binding.productsRv.adapter=adapter
        viewModel.products.observe(viewLifecycleOwner){
            when(it.status){
                is Loading -> binding.progressBar.isVisible =true
                is Success->{
                    if(!it.status.data.isNullOrEmpty()) {
                        binding.progressBar.isVisible = false
                        adapter.setProducts(ArrayList(it.status.data))
                    }
                }
                is il.co.syntax.finalkotlinproject.utils.Error -> {
                    binding.progressBar.isVisible=false
                    Toast.makeText(requireContext(),it.status.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onProductClick(productId: Int) {
        findNavController().navigate(R.id.action_allProductsFragment_to_singleProductFragment,
            bundleOf("id" to productId)
        )
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.logout,menu)


        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (auth.currentUser!=null)
        {
            when(item.itemId){
                R.id.logout_action->
                {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle(getString(R.string.logout)).setMessage(getString(R.string.are_you_sure_you_want_to_sign_out)).
                            setPositiveButton(getString(R.string.yes)){
                                p0,p1->firebaseViewModel.signOut()
                                viewModel.deleteAll()
                                Toast.makeText(requireContext(),getString(R.string.sign_out_successful),Toast.LENGTH_SHORT).show()
                            }

                    builder.show()

                    return true
                }
                R.id.shopping_cart_item ->
                {
                    findNavController().navigate(R.id.action_allProductsFragment_to_shoppingCartFragment2)
                }
                R.id.account_details ->
                {
                    try {
                        val db = Firebase.firestore
                        val users = db.collection("users")
                        val docRef = users.document(auth.currentUser!!.uid)
                        docRef.get().addOnSuccessListener {
                            if(it!=null)
                            {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setTitle(getString(R.string.account_details))
                                builder.setMessage(getString(R.string.username)+" : ${it.data?.get("userName")}\n" +
                                        getString(R.string.email)+": ${it.data?.get("email")}\n" +
                                        getString(R.string.phone_no)+": ${it.data?.get("phoneNumber")}")
                                builder.show()
                            }
                            else
                            {
                                Toast.makeText(requireContext(),getString(R.string.no_data), Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(),"$it", Toast.LENGTH_SHORT).show()
                        }
                    }
                    catch (e: java.lang.Exception)
                    {
                        Toast.makeText(requireContext(),getString(R.string.no_one_is_connect),Toast.LENGTH_SHORT).show()
                    }
                    return true

                }
                R.id.login_action->
                {
                    Toast.makeText(requireContext(),getString(R.string.you_logged_in_already),Toast.LENGTH_SHORT).show()
                }
            }
        }
        else
        {
            when(item.itemId){
                R.id.logout_action->
                {
                    Toast.makeText(requireContext(),getString(R.string.there_is_no_account),Toast.LENGTH_SHORT).show()
                    return true
                }
                R.id.login_action->
                {
                    findNavController().navigate(R.id.action_allProductsFragment_to_loginScreen)
                    return true
                }
                R.id.account_details->
                {
                    Toast.makeText(requireContext(),getString(R.string.there_is_no_account),Toast.LENGTH_SHORT).show()
                    return true
                }
                R.id.shopping_cart_item->
                {
                    Toast.makeText(requireContext(),getString(R.string.there_is_no_account),Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}