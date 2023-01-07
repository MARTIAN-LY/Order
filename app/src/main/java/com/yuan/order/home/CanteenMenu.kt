package com.yuan.order.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuan.order.databinding.FragmentCateenMenuBinding
import com.yuan.order.home.model.Dish
import com.yuan.order.home.model.DishRecyclerAdapter
import com.yuan.order.home.model.DishRecyclerDecoration
import com.yuan.order.util.InputUtils

class CanteenMenu(
    private val canteenId: Short
) : Fragment() {

    private var _binding: FragmentCateenMenuBinding? = null
    private val binding get() = _binding!!
    private val dishes = ArrayList<Dish>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCateenMenuBinding.inflate(inflater, container, false)
        getData()
        initRecycler()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        for (dish in InputUtils.list) {
            if (dish.canteen == canteenId) {
                dishes.add(dish)
            }
        }
    }

    private fun initRecycler() {
        binding.cRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DishRecyclerDecoration(30))
            adapter = DishRecyclerAdapter(dishes)
        }
    }
}