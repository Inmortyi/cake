package com.example.cake.MAIN

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cake.Adapters.CartAdapter
import com.example.cake.Models.PopularModel
import com.example.cake.Models.SharedModel
import com.example.cake.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var sharedModel: SharedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedModel = ViewModelProvider(requireActivity()).get(SharedModel::class.java)


        adapter = CartAdapter(requireContext(), sharedModel.cartItem.value ?: mutableListOf()) { items ->
            updateTotalPrice(items)
        }


        binding.cartRV.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRV.adapter = adapter


        sharedModel.cartItem.value?.let {
            updateTotalPrice(it)
        }
        binding.proceedBtn.setOnClickListener {
            val items = sharedModel.cartItem.value ?: listOf()
            val orderItemsString = items.joinToString("\n") { "${it.getFoodName()} x ${it.getFoodCount()}" }
            val totalPrice = calculateTotalPrice(items)
            val intent = Intent(requireContext(), PayActivity::class.java).apply {

                putExtra("totalPrice", calculateTotalPrice(sharedModel.cartItem.value ?: listOf()))
                putExtra("orderItems", orderItemsString)

            }
            startActivity(intent)
        }
    }

    private fun updateTotalPrice(items: List<PopularModel>) {
        val totalPrice = calculateTotalPrice(items)
        binding.menuTotalSum.text = totalPrice.toString()
    }

    private fun calculateTotalPrice(items: List<PopularModel>): Int {
        var totalPrice = 0
        for (item in items) {
            totalPrice += item.getFoodPrice() * item.getFoodCount()
        }
        return totalPrice
    }
}