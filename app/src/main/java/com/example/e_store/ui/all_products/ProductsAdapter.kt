package com.example.e_store.ui.all_products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_store.data.models.Product
import com.example.e_store.databinding.ProductItemBinding

class ProductsAdapter(private val listener:ProductItemListener) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>(){

    private val products=ArrayList<Product>()
    interface ProductItemListener{
        fun onProductClick(characterId:Int)
    }

    class ProductViewHolder(private val  itemBinding:ProductItemBinding, private val listener:ProductItemListener)
        :RecyclerView.ViewHolder(itemBinding.root),View.OnClickListener{
            private lateinit var product: Product
            init{
                itemBinding.root.setOnClickListener(this)
            }

            fun bind(item: Product){
                this.product=item
                itemBinding.productTitle.text=item.title
                itemBinding.itemPrice.text=item.price+" $"
                Glide.with(itemBinding.root).load(item.image).into(itemBinding.image)
            }

        override fun onClick(p0: View?) {
            listener.onProductClick(product.id)
        }
    }

    fun setProducts(products:Collection<Product>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =holder.bind(products[position])
    override fun getItemCount()=products.size


}