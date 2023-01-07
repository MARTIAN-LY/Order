package com.yuan.order.home.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuan.order.R
import com.yuan.order.activity.MainActivity
import com.yuan.order.databinding.RecyclerDishBinding

class DishRecyclerAdapter(private val dishes: List<Dish>) :
    RecyclerView.Adapter<DishRecyclerAdapter.DishViewHolder>() {

    /** ViewHolder 是单个 Item 的容器 */
    class DishViewHolder(val binding: RecyclerDishBinding) : RecyclerView.ViewHolder(binding.root)

    /** 创建 ViewHolder */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDishBinding.inflate(layoutInflater, parent, false)
        return DishViewHolder(binding)
    }

    /** 绑定数据 */
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.binding.apply {
            val dish = dishes[position]
            dishTextRestaurant.text = dish.restaurant
            dishTextName.text = dish.name
            dishTextPrice.text = "单价：${dish.price}元"
            dishTextDescrip.text = dish.description

            dishBtnBuy.setOnClickListener {
                var num = 0
                MainActivity.apply {
                    if (dishList.contains(dish)) {
                        val index = dishList.indexOf(dish)
                        numList[index] = numList[index] + 1
                        num = numList[index]
                    } else {
                        dishList.add(dish)
                        numList.add(1)
                        num = 1
                    }
                    dishTextNum.text = "已购：$num"
                }
            }
        }
    }

    /** Item 个数 */
    override fun getItemCount() = dishes.size
}