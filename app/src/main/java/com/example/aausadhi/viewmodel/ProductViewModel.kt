package com.example.aausadhi.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.aausadhi.model.ProductModel
import com.example.aausadhi.repository.ProductRepository

class ProductViewModel (val repo: ProductRepository){

    fun addProduct(product: ProductModel, callback:(Boolean, String)->Unit){
        repo.addProduct(product,callback)
    }
    fun updateProduct(id:String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit){
        repo.updateProduct(id,data,callback)
    }
    fun deleteProduct(id:String,callback: (Boolean, String) -> Unit){
        repo.deleteProduct(id,callback)
    }
    var _products = MutableLiveData<ProductModel?>()
    var products = MutableLiveData<ProductModel?>()
        get() = _products

    var _allProducts = MutableLiveData<List<ProductModel>?>()
    var allProducts = MutableLiveData<List<ProductModel>?>()
        get() = _allProducts


    fun getProductById(productId:String){
        repo.getProductById(productId){
                product,success,message->
            if(success){
                _products.value = product
            }
        }
    }

    var _loadingState= MutableLiveData<Boolean>() /// make variable for particular variable
    var loadingState= MutableLiveData<Boolean>()
        get()=_loadingState
    fun getAllProduct(){
        _loadingState.value = true
        repo.getAllProducts(){
                products, success, message ->
            if(success){
                _allProducts.value = products
                _loadingState.value = false
            }
        }
    }
}