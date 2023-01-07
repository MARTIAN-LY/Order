package com.yuan.order.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuan.order.activity.HelpActivity2
import com.yuan.order.databinding.FragmentHomeBinding
import com.yuan.order.util.InputUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // 设置四个滑动页面
        initViewPager()
        binding.homeTextSearch.setOnClickListener {
            startActivity(Intent(activity, HelpActivity2::class.java))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewPager() {

        //设置ViewPager
        binding.viewPager.adapter =
            object : FragmentStateAdapter(this) {
                override fun getItemCount() = 4
                override fun createFragment(position: Int) = when (position) {
                    0 -> CanteenMenu(InputUtils.CANTEEN1)
                    1 -> CanteenMenu(InputUtils.CANTEEN2)
                    2 -> CanteenMenu(InputUtils.CANTEEN3)
                    else -> CanteenMenu(InputUtils.CANTEEN4)
                }
            }
        // 把 ViewPager 和 TabLayout 关联
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "一食堂"
                1 -> "二食堂"
                2 -> "三食堂"
                else -> "四食堂"
            }
        }.attach()

        //TabLayout点击事件
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

}