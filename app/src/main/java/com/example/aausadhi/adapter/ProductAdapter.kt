package com.example.pharmabucket.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmabucket.R
import com.example.pharmabucket.model.ProductModel
import com.example.pharmabucket.ui.activity.UpdateProductActivity

class ProductAdapter(
    private val context: Context,
    private var productList: ArrayList<ProductModel>
):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    class ProductViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val pName: TextView = itemView.findViewById(R.id.Name)
        val pDesc: TextView = itemView.findViewById(R.id.Desc)
        val pPrice: TextView = itemView.findViewById(R.id.Price)
        val Category: TextView = itemView.findViewById(R.id.Category)
        val Dosage: TextView = itemView.findViewById(R.id.Dosage)
        val edit: TextView = itemView.findViewById(R.id.editSingle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    val product= productList[position]
        holder.pName.text=product.name
        holder.pDesc.text=product.description
        holder.pPrice.text=product.price.toString()
        holder.Category.text=product.category
        holder.Dosage.text=product.Dosage
        holder.edit.setOnClickListener{
        val intent=Intent(context,UpdateProductActivity::class.java)
        intent.putExtra("id",productList[position].ID)
        context.startActivity(intent)
        }
        }
    fun updateData(products: List<ProductModel>?){
        productList.clear()
        if (products != null) {
            productList.addAll(products)
        }
        notifyDataSetChanged()
    }

    fun getProductId(position: Int):String{
        return productList[position].ID
    }

}
