package com.example.firebaselearn.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaselearn.R
import com.example.firebaselearn.databinding.ActivityUpdateProductBinding
import com.example.firebaselearn.repository.ProductRepositoryImpl
import com.example.firebaselearn.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProductBinding
    private lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productid= intent.getStringExtra("id").toString()
        productViewModel= ProductViewModel(ProductRepositoryImpl())
        binding=ActivityUpdateProductBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        productViewModel.getProductById(productid.toString())

        productViewModel.products.observe(this){
            binding.upprodNameInput.setText(it?.name.toString())
            binding.upprodPriceInput.setText(it?.price.toString().toInt())
            binding.upprodDescInput.setText(it?.description.toString())
            binding.upprodCatInput.setText(it?.category.toString())
            binding.upprodDosageInput.setText(it?.Dosage.toString())
        }
        binding.upsubmitProduct.setOnClickListener {
          val name=  binding.upprodNameInput.text.toString()
           val price= binding.upprodPriceInput.text.toString().toInt()
          val desc=  binding.upprodDescInput.text.toString()
         val cat=   binding.upprodCatInput.text.toString()
            val dosage=binding.upprodDosageInput.text.toString()

            var updatedMap= mutableMapOf<String,Any>()

            updatedMap["name"]=name;
            updatedMap["price"]=price;
            updatedMap["description"]=desc;
            updatedMap["category"]=cat;
            updatedMap["Dosage"]=dosage;

            productViewModel.updateProduct(productid.toString(),updatedMap){
                success,messaage->
                if(success){
                    Toast.makeText(this@UpdateProductActivity,messaage,Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateProductActivity,messaage,Toast.LENGTH_LONG).show()
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