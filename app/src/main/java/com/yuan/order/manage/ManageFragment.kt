package com.yuan.order.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuan.order.R
import com.yuan.order.databinding.FragmentManageBinding


class ManageFragment : Fragment() {

    private var _binding: FragmentManageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }


    private fun initViewPager() {
        //设置ViewPager
        binding.manageViewpager.adapter =
            object : FragmentStateAdapter(this) {
                override fun getItemCount() = 3
                override fun createFragment(position: Int) = when (position) {
                    0 -> MOrderFragment()
                    1 -> MRestaurantFragment()
                    else -> MCanteenFragment()
                }
            }
        // 把 ViewPager 和 TabLayout 关联
        TabLayoutMediator(binding.manageTab, binding.manageViewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "订单"
                1 -> "商户"
                else -> "食堂"

            }

            when (position) {
                0 -> tab.setIcon(R.drawable.ic_order)
                1 -> tab.setIcon(R.drawable.ic_restaurant)
                else -> tab.setIcon(R.drawable.ic_school)
            }
        }.attach()

        //TabLayout点击事件
        binding.manageTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.manageViewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        // 滑动切换 tab
        binding.manageViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.manageTab.selectTab(binding.manageTab.getTabAt(position))
            }
        })
    }

}