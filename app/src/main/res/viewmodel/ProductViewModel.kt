package com.example.firebaselearn.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.firebaselearn.model.ProductModel
import com.example.firebaselearn.repository.ProductRepository

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
    var _product =MutableLiveData<ProductModel?>()
    var products =MutableLiveData<ProductModel?>()
        get()= _product

    var _allProducts =MutableLiveData<List<ProductModel>?>()
    var allProducts =MutableLiveData<List<ProductModel>?>()
        get()= _allProducts

    fun getProductById(id:String){
        repo.getProductById(id){
            product,success,message->
            if(success){
                _product.value=product
            }
        }}

    var _loadingState= MutableLiveData<Boolean>() /// make variable for particular variable
    var loadingState= MutableLiveData<Boolean>()
        get()=_loadingState
    fun getAllProduct(){
        _loadingState.value = true
        repo.getAllProducts{
                products, success, message ->
            if(success){
                _allProducts.value = products
                _loadingState.value = false
            }
        }
    }
        }

