package com.example.e_store.ui.shopping_cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_store.data.models.CartItem

import com.example.e_store.databinding.CartItemBinding


class ShoppingCartAdapter(private val listener: ItemListener):RecyclerView.Adapter<ShoppingCartAdapter.CartItemViewHolder> (){
    private val items=ArrayList<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding=CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartItemViewHolder(binding, listener)
    }
    interface ItemListener{
        fun onItemClicked(index:Int)
    }

    class CartItemViewHolder(private val  itemBinding: CartItemBinding, private val listener: ItemListener)
        : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {
        private lateinit var item: CartItem

        init {
            itemBinding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClicked(item.id)
        }

        fun bind(item: CartItem){
            this.item=item
            itemBinding.productTitle.text=item.title
            itemBinding.itemPrice.text=item.price
            itemBinding.quantity.text=item.quantity.toString()
            Glide.with(itemBinding.root).load(item.image).into(itemBinding.image)
        }
    }

    fun setProducts(items:Collection<CartItem>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    fun itemAt(position: Int)=items[position]

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount()=items.size
}