package com.example.aausadhi.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aausadhi.R
import com.example.aausadhi.databinding.ActivityAddProductBinding
import com.example.aausadhi.model.ProductModel
import com.example.aausadhi.repository.ProductRepositoryImpl
import com.example.aausadhi.utils.LoadingUtils
import com.example.aausadhi.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {
    private lateinit var loadingUtils: LoadingUtils
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var productVM: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ProductRepositoryImpl()
        productVM = ProductViewModel(repo)
        loadingUtils = LoadingUtils(this)

        binding.addProductCancel.setOnClickListener {
            finish()
        }

        binding.submitProduct.setOnClickListener {
            if (validateForm()) {
                loadingUtils.show()
                val name = binding.prodNameInput.text.toString()
                val desc = binding.prodDescInput.text.toString()
                val price = binding.prodPriceInput.text.toString().toInt()
                val dose = binding.prodDosageInput.text.toString()
                val category = binding.prodCatInput.text.toString()
                val model = ProductModel("", name, desc, price, category, dose)

                productVM.addProduct(model) { success, message ->
                    loadingUtils.dismiss()
                    if (success) {
                        Toast.makeText(this@AddProductActivity, message, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddProductActivity, message, Toast.LENGTH_SHORT).show()
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

        val name = binding.prodNameInput.text.toString()
        val desc = binding.prodDescInput.text.toString()
        val priceText = binding.prodPriceInput.text.toString()
        val dose = binding.prodDosageInput.text.toString()
        val category = binding.prodCatInput.text.toString()

        if (name.isEmpty()) {
            binding.prodNameInput.error = "Product name is required"
            isValid = false
        }

        if (desc.isEmpty()) {
            binding.prodDescInput.error = "Product description is required"
            isValid = false
        }

        if (priceText.isEmpty()) {
            binding.prodPriceInput.error = "Product price is required"
            isValid = false
        } else {
            try {
                val price = priceText.toInt()
                if (price <= 0) {
                    binding.prodPriceInput.error = "Product price must be greater than zero"
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                binding.prodPriceInput.error = "Invalid price format"
                isValid = false
            }
        }

        if (dose.isEmpty()) {
            binding.prodDosageInput.error = "Dosage is required"
            isValid = false
        }

        if (category.isEmpty()) {
            binding.prodCatInput.error = "Category is required"
            isValid = false
        }

        return isValid
    }
}
