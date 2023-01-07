package com.yuan.order.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuan.order.activity.MainActivity
import com.yuan.order.databinding.FragmentMOrderBinding
import com.yuan.order.home.model.DishRecyclerDecoration
import com.yuan.order.manage.model.MOrderAdapter

class MOrderFragment : Fragment() {

    private var _binding: FragmentMOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMOrderBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        binding.mOrderRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DishRecyclerDecoration(30))
            adapter = MOrderAdapter()
        }
    }
}