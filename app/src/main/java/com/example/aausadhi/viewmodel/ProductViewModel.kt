package com.example.aausadhi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aausadhi.model.ProductModel
import com.example.aausadhi.repository.ProductRepository

class ProductViewModel(private val repo: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<ProductModel?>()
    val products: MutableLiveData<ProductModel?>
        get() = _products

    private val _allProducts = MutableLiveData<List<ProductModel>?>()
    val allProducts: MutableLiveData<List<ProductModel>?>
        get() = _allProducts

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: MutableLiveData<Boolean>
        get() = _loadingState

    fun addProduct(product: ProductModel, callback: (Boolean, String) -> Unit) {
        repo.addProduct(product, callback)
    }

    fun updateProduct(id: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        repo.updateProduct(id, data, callback)
    }

    fun deleteProduct(id: String, callback: (Boolean, String) -> Unit) {
        repo.deleteProduct(id, callback)
    }

    fun getProductById(productId: String) {
        repo.getProductById(productId) { product, success, _ ->
            if (success) {
                _products.value = product
            }
        }
    }

    fun getAllProduct() {
        _loadingState.value = true
        repo.getAllProducts { products, success, _ ->
            if (success) {
                _allProducts.value = products
                _loadingState.value = false
            }
        }
    }

    fun searchProducts(query: String) {
        _loadingState.value = true
        repo.searchProducts(query) { products, success, _ ->
            if (success) {
                _allProducts.value = products
                _loadingState.value = false
            }
        }
    }
}
