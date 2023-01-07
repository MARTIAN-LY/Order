package com.yuan.order.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuan.order.R
import com.yuan.order.databinding.FragmentMOrderBinding
import com.yuan.order.databinding.FragmentMRestaurantBinding
import com.yuan.order.home.model.DishRecyclerDecoration
import com.yuan.order.manage.model.MOrderAdapter

class MRestaurantFragment : Fragment() {
    private var _binding: FragmentMRestaurantBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.include1.apply {
            btnMstaffBuy.setOnClickListener {
                btnMstaffBuy.visibility = View.GONE
                textMstaffState.text = "订单已完成"
                Toast.makeText(context, "完成订单！！！", Toast.LENGTH_SHORT).show()
            }
        }
        binding.include2.apply {
            textMstaffPhone.text = "下单人手机号：555555"
            textMstaffContent.text = "订单内容：鲜肉包  x5"
            textMstaffTotal.text = "实付款：10元"
            btnMstaffBuy.visibility = View.GONE
            textMstaffState.text = "订单已完成"
        }
        binding.include3.apply {
            textMstaffPhone.text = "下单人手机号：1234567890"
            textMstaffContent.text = "订单内容：鲜肉包  x1"
            textMstaffTotal.text = "实付款：2元"
            btnMstaffBuy.visibility = View.GONE
            textMstaffState.text = "订单已完成"
        }
    }
}