package com.example.aausadhi.repository

import com.example.aausadhi.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepositoryImpl : ProductRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("products")

    override fun addProduct(product: ProductModel, callback: (Boolean, String) -> Unit) {
        val id = reference.push().key.toString()
        product.id = id
        reference.child(id).setValue(product).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Added Successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun updateProduct(id: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        reference.child(id).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Updated Successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteProduct(id: String, callback: (Boolean, String) -> Unit) {
        reference.child(id).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Deleted Successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getProductById(id: String, callback: (ProductModel?, Boolean, String) -> Unit) {
        reference.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val model = snapshot.getValue(ProductModel::class.java)
                callback(model, true, "Data fetched")
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllProducts(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<ProductModel>()
                for (eachProduct in snapshot.children) {
                    val model = eachProduct.getValue(ProductModel::class.java)
                    model?.let { products.add(it) }
                }
                callback(products, true, "Fetched")
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun searchProducts(query: String, callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        reference.orderByChild("name").startAt(query).endAt("$query\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = mutableListOf<ProductModel>()
                    for (eachProduct in snapshot.children) {
                        val model = eachProduct.getValue(ProductModel::class.java)
                        model?.let { products.add(it) }
                    }
                    callback(products, true, "Fetched")
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null, false, error.message)
                }
            })
    }
}
