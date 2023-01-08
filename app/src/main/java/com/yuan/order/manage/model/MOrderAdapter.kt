package com.yuan.order.manage.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yuan.order.activity.MainActivity
import com.yuan.order.databinding.RecyclerOrderBinding

class MOrderAdapter :
    RecyclerView.Adapter<MOrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: RecyclerOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerOrderBinding.inflate(layoutInflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.binding.apply {
            val num = MainActivity.numList[position]
            val dish = MainActivity.dishList[position]

            holder.binding.apply {
                textMorderName.text = "${dish.name}(${dish.restaurant})"
                textMorderNum.text = "数量：$num"
                textMorderSingle.text = "单价：${dish.price}元"
                textMorderTotal.text = "合计 ${dish.price * num} 元"
                btnMorderBuy.setOnClickListener {
                    Toast.makeText(root.context, "购买成功", Toast.LENGTH_SHORT).show()
                    textState.text = "已付款"
                    btnMorderBuy.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount() = MainActivity.dishList.size
}