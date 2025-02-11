package com.example.cake.MAIN

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.cake.Adapters.ImageSliderAdapter
import com.example.cake.Adapters.PopularAdapter
import com.example.cake.MenuBottomSheerFragment
import com.example.cake.Models.PopularModel
import com.example.cake.Models.SharedModel
import com.example.cake.R

class HomeFragment : Fragment() {

    private lateinit var viewPager2 : ViewPager2
    private lateinit var adapter : ImageSliderAdapter
    private lateinit var imageList : ArrayList<Int>
    private lateinit var handler: Handler

    private lateinit var popularAdapter : PopularAdapter
    private lateinit var listPopular : ArrayList<PopularModel>
    private lateinit var homeRV : RecyclerView
    private lateinit var goTextMenu : TextView

    private lateinit var shareModel : SharedModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        viewPager2 = view.findViewById(R.id.imageSlider)

        shareModel = ViewModelProvider(requireActivity()).get(SharedModel :: class.java)


        homeRV = view.findViewById(R.id.home_RV)

        goTextMenu = view.findViewById(R.id.go_menu)
        goTextMenu.setOnClickListener {
            val bottomSheetMenu = MenuBottomSheerFragment()
            bottomSheetMenu.show(parentFragmentManager, "Test")
        }


        var act: Main_Window_Activity

        act = (activity as Main_Window_Activity)


        listPopular = act.listPopularItems

        popularAdapter = PopularAdapter(requireContext(), listPopular)

        if (act.listNotifiers.contains(popularAdapter)) {

            //act.loadDataset()
            act.updateNotifiers()
        } else {

            act.addNotifier(popularAdapter)
        }


        popularAdapter.setShareModel(shareModel)

        homeRV.layoutManager = LinearLayoutManager(requireContext())
        homeRV.adapter = popularAdapter






        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setTransfarmer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager2.removeCallbacks(runnable)
                viewPager2.postDelayed(runnable, 2000)
            }
        })


    }

    private val runnable = Runnable{
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setTransfarmer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(10))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)


    }

    override fun onPause() {
        super.onPause()
        viewPager2.removeCallbacks(runnable)
    }

    override fun onStart() {
        super.onStart()
        viewPager2.postDelayed(runnable, 2000)
    }

    private fun init() {


        imageList = ArrayList()
        adapter = ImageSliderAdapter(requireContext(), imageList, viewPager2)
        handler = Handler(Looper.myLooper()!!)

        imageList.add(R.drawable._226962)
        imageList.add(R.drawable._452042)
        imageList.add(R.drawable._817159)
        imageList.add(R.drawable.banner_4)
        imageList.add(R.drawable.banner_5)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

}






















