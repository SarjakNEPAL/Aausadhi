package com.example.aausadhi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aausadhi.R
import com.example.aausadhi.model.ProductModel
import com.example.aausadhi.ui.activity.UpdateProductActivity

class SearchProductAdapter(
    private val context: Context,
    private var productList: ArrayList<ProductModel>
) : RecyclerView.Adapter<SearchProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val product = productList[position]
        holder.pName.text = product.name
        holder.pDesc.text = product.description
        holder.pPrice.text = product.price.toString()
        holder.Category.text = product.category
        holder.Dosage.text = product.dosage
        holder.edit.setOnClickListener{
            val intent= Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("id",productList[position].id)
            context.startActivity(intent)
            (context as? FragmentActivity)?.finish()
        }
    }

    fun updateData(products: List<ProductModel>?) {
        productList.clear()
        products?.let { productList.addAll(it) }
        notifyDataSetChanged()
    }

    fun getProductId(position: Int): String {
        return productList[position].id
    }
}
