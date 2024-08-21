package com.example.cake.MAIN

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cake.Adapters.PopularAdapter
import com.example.cake.Models.PopularModel
import com.example.cake.Models.SharedModel
import com.example.cake.databinding.FragmentSearcBinding

class SearcFragment : Fragment() {

    private lateinit var  binding : FragmentSearcBinding
    private lateinit var  adapter: PopularAdapter
    private lateinit var  list: ArrayList<PopularModel>
    private lateinit var shareModel : SharedModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearcBinding.inflate(inflater, container, false )

        shareModel = ViewModelProvider(requireActivity()).get(SharedModel :: class.java)





        var act: Main_Window_Activity

        act = (activity as Main_Window_Activity)


        list = act.listPopularItems

        adapter = PopularAdapter(requireContext(), list)

        if (act.listNotifiers.contains(adapter)) {
            Log.d("TEST", "Contains")
            //act.loadDataset()
            act.updateNotifiers()
        } else {
            Log.d("TEST", "Not contains")
            act.addNotifier(adapter)
        }


        adapter = PopularAdapter(requireContext(), list)

        adapter.setShareModel(shareModel)

        binding.searchMenuRv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchMenuRv.adapter = adapter

        searchMenuFood()

        return binding.root
    }

    private fun searchMenuFood(){

        binding.searchMenuItem.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
              return true
            }
        })

    }


    private fun filterList(input : String?){

        val filteredList = if (input.isNullOrEmpty()){
            list
        }else{
            list.filter{item ->
                item.getFoodName()!!.contains(input, ignoreCase = true)
            }

        }

        adapter.updateList(filteredList as ArrayList<PopularModel>)

    }




}