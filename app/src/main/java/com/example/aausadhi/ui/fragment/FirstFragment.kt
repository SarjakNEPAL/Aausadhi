package com.example.aausadhi.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aausadhi.R
import com.example.aausadhi.adapter.ProductAdapter
import com.example.aausadhi.databinding.FragmentFirstBinding
import com.example.aausadhi.repository.ProductRepositoryImpl
import com.example.aausadhi.ui.activity.AddProductActivity
import com.example.aausadhi.viewmodel.ProductViewModel
import java.util.ArrayList

class FirstFragment : Fragment() {
    lateinit var binding:FragmentFirstBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductAdapter(requireContext(), ArrayList())

        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        productViewModel.getAllProduct()

        productViewModel.allProducts.observe(viewLifecycleOwner) { product ->
            product?.let {
                adapter.updateData(it)
            }
        }

        productViewModel.loadingState.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val productId = adapter.getProductId(viewHolder.adapterPosition)

                productViewModel.deleteProduct(productId) { success, message ->
                    if (success) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }).attachToRecyclerView(binding.recyclerView)

        binding.addProductButton.setOnClickListener {
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
