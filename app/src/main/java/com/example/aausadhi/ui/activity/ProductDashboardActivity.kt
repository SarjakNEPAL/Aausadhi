package com.example.pharmabucket.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pharmabucket.R
import com.example.pharmabucket.adapter.ProductAdapter
import com.example.pharmabucket.databinding.ActivityProductDashboardBinding
import com.example.pharmabucket.repository.ProductRepositoryImpl
import com.example.pharmabucket.viewmodel.ProductViewModel
import java.util.ArrayList

class ProductDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDashboardBinding
    private lateinit var adapter:ProductAdapter
    private lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDashboardBinding.inflate(layoutInflater)

        productViewModel=ProductViewModel(ProductRepositoryImpl())
        adapter=ProductAdapter(this@ProductDashboardActivity, ArrayList())
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){  // for deleting by siping
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val productId=adapter.getProductId(viewHolder.adapterPosition) // gets the selected product id
                productViewModel.deleteProduct(productId){
                    success,message->
                    if(success){
                        Toast.makeText(this@ProductDashboardActivity, message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ProductDashboardActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }).attachToRecyclerView(binding.recyclerView)
        enableEdgeToEdge()
        setContentView(binding.root)
        productViewModel.getAllProduct()
        productViewModel.allProducts.observe(this){
            product->
            product?.let{
                adapter.updateData(product);
            }
        }

        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(this@ProductDashboardActivity)

        productViewModel.loadingState.observe(this){
            loading->
            if (loading){
                binding.progressBar.visibility= View.VISIBLE
            }else{
            binding.progressBar.visibility=View.GONE
        }

        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this@ProductDashboardActivity,AddProductActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            }
        }
    }
}