package com.example.firebaselearn.repository

import com.example.firebaselearn.model.ProductModel

interface ProductRepository {
    fun addProduct(product: ProductModel, callback:(Boolean, String)->Unit)
    fun updateProduct(id:String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit)
    fun deleteProduct(id:String,callback: (Boolean, String) -> Unit)
    fun getProductById(id:String,callback: (ProductModel?, Boolean, String) -> Unit)
    fun getAllProducts(callback: (List<ProductModel>?, Boolean, String) -> Unit)
}