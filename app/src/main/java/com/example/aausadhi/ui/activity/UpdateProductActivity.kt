package com.example.aausadhi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aausadhi.R
import com.example.aausadhi.databinding.ActivityUpdateProductBinding
import com.example.aausadhi.repository.ProductRepositoryImpl
import com.example.aausadhi.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProductBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productid = intent.getStringExtra("id").toString()
        productViewModel = ProductViewModel(ProductRepositoryImpl())
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        productViewModel.getProductById(productid)

        productViewModel.products.observe(this) {
            binding.upprodNameInput.setText(it?.name ?: getString(R.string.product_name))
            binding.upprodPriceInput.setText(it?.price?.toString() ?: getString(R.string.price))
            binding.upprodDescInput.setText(it?.description ?: getString(R.string.product_description))
            binding.upprodCatInput.setText(it?.category ?: getString(R.string.category))
            binding.upprodDosageInput.setText(it?.dosage ?: getString(R.string.dosage))
        }

        binding.cancelButton.setOnClickListener {
            finish()
            val intent = Intent(this@UpdateProductActivity, ProductDashboardActivity::class.java)
            startActivity(intent)
        }

        binding.upsubmitProduct.setOnClickListener {
            if (validateForm()) {
                val name = binding.upprodNameInput.text.toString()
                val price = binding.upprodPriceInput.text.toString().toInt()
                val desc = binding.upprodDescInput.text.toString()
                val cat = binding.upprodCatInput.text.toString()
                val dosage = binding.upprodDosageInput.text.toString()

                val updatedMap = mutableMapOf<String, Any>()
                updatedMap["name"] = name
                updatedMap["price"] = price
                updatedMap["description"] = desc
                updatedMap["category"] = cat
                updatedMap["Dosage"] = dosage

                productViewModel.updateProduct(productid, updatedMap) { success, message ->
                    if (success) {
                        Toast.makeText(this@UpdateProductActivity, message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@UpdateProductActivity, ProductDashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@UpdateProductActivity, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val name = binding.upprodNameInput.text.toString().trim()
        val desc = binding.upprodDescInput.text.toString().trim()
        val priceText = binding.upprodPriceInput.text.toString().trim()
        val dose = binding.upprodDosageInput.text.toString().trim()
        val category = binding.upprodCatInput.text.toString().trim()

        if (name.isEmpty()) {
            binding.upprodNameInput.error = "Product name is required"
            isValid = false
        }

        if (desc.isEmpty()) {
            binding.upprodDescInput.error = "Product description is required"
            isValid = false
        }

        if (priceText.isEmpty()) {
            binding.upprodPriceInput.error = "Product price is required"
            isValid = false
        } else {
            try {
                val price = priceText.toInt()
                if (price <= 0) {
                    binding.upprodPriceInput.error = "Product price must be greater than zero"
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                binding.upprodPriceInput.error = "Invalid price format"
                isValid = false
            }
        }

        if (dose.isEmpty()) {
            binding.upprodDosageInput.error = "Dosage is required"
            isValid = false
        }

        if (category.isEmpty()) {
            binding.upprodCatInput.error = "Category is required"
            isValid = false
        }

        return isValid
    }
}
