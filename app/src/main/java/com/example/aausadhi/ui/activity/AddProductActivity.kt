package com.example.pharmabucket.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pharmabucket.R
import com.example.pharmabucket.databinding.ActivityAddProductBinding
import com.example.pharmabucket.model.ProductModel
import com.example.pharmabucket.repository.ProductRepository
import com.example.pharmabucket.repository.ProductRepositoryImpl
import com.example.pharmabucket.utils.LoadingUtils
import com.example.pharmabucket.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {
    private lateinit var loadingUtils: LoadingUtils
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var productVM:ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo= ProductRepositoryImpl()
        productVM= ProductViewModel(repo)

        loadingUtils=LoadingUtils(this)

        binding.submitProduct.setOnClickListener {
            loadingUtils.show()
            val name= binding.prodNameInput.text.toString()
            val desc= binding.prodDescInput.text.toString()
            val price= binding.prodPriceInput.text.toString().toInt()
            val dose= binding.prodNameInput.text.toString()
            val category= binding.prodCatInput.text.toString()
            val model= ProductModel("",name,desc,price,category,dose)
            productVM.addProduct(model){
                success, message->
                    loadingUtils.dismiss()
                    if(success){
                        Toast.makeText(this@AddProductActivity,message,Toast.LENGTH_SHORT).show()
                    finish()
                    }else
                    {
                        Toast.makeText(this@AddProductActivity,message,Toast.LENGTH_SHORT).show()
                    }
            }
            

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}