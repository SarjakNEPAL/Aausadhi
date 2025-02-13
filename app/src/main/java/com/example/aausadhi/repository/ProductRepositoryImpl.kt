package com.example.pharmabucket.repository

import com.example.pharmabucket.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepositoryImpl: ProductRepository {

    val database: FirebaseDatabase= FirebaseDatabase.getInstance()
    val reference: DatabaseReference = database.reference.child("products")

    override fun addProduct(product: ProductModel, callback: (Boolean, String) -> Unit) {
        var id=reference.push().key.toString() //randomly generating I
        reference.child(id).setValue(product)
        product.ID=id
        reference.child(id).setValue(product).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Product Added Successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }

        }
    }

    override fun updateProduct(
        id: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(id).updateChildren(data).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Product Updated Successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }

        }
    }

    override fun deleteProduct(id: String, callback: (Boolean, String) -> Unit) {
        reference.child(id).removeValue().addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Product Deleted Successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun getProductById(id: String, callback: (ProductModel?, Boolean, String) -> Unit) {
    reference.child(id).addValueEventListener(
        object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val model= snapshot.getValue(ProductModel::class.java)
                    callback(model,true,"Data fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message.toString())
            }
        }

    )
    }

    override fun getAllProducts(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var products = mutableListOf<ProductModel>()
                if(snapshot.exists()){
                    for(eachProduct in snapshot.children){
                        var model = eachProduct.getValue(ProductModel::class.java)
                        if(model != null){
                            products.add(model)
                        }
                    }
                    callback(products,true,"fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message.toString())
            }
        })
    }
}