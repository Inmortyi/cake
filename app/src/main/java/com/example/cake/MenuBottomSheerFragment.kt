package com.example.cake

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cake.Adapters.PopularAdapter
import com.example.cake.MAIN.Main_Window_Activity
import com.example.cake.Models.PopularModel
import com.example.cake.Models.SharedModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheerFragment : BottomSheetDialogFragment() {
    private lateinit var adapter : PopularAdapter
    private lateinit var menuList : ArrayList<PopularModel>
    private lateinit var menuRv : RecyclerView
    private lateinit var shareModel : SharedModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_bottom_sheer, container, false)

        shareModel = ViewModelProvider(requireActivity()).get(SharedModel :: class.java)


      //  Log.d("TEST", "Before")
        var act: Main_Window_Activity

        act = (activity as Main_Window_Activity)


        menuList = act.listPopularItems

        adapter = PopularAdapter(requireContext(), menuList)

        if (act.listNotifiers.contains(adapter)) {
            Log.d("TEST", "Contains")
            //act.loadDataset()
            act.updateNotifiers()
        } else {
            Log.d("TEST", "Not contains")
            act.addNotifier(adapter)
        }


        adapter = PopularAdapter(requireContext(), menuList)
        adapter.setShareModel(shareModel)

        menuRv = view.findViewById(R.id.menu_RV)


        menuRv.layoutManager = LinearLayoutManager(requireContext())
        menuRv.adapter = adapter

        return view
    }


}