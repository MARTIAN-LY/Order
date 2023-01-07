package com.yuan.order.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yuan.order.R
import com.yuan.order.databinding.ActivityMainBinding
import com.yuan.order.home.model.Dish

class MainActivity : BaseActivity() {

    companion object {
        val dishList = ArrayList<Dish>()
        val numList = ArrayList<Int>()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 切换界面
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

        // 这种方法不行
        // val navController = Navigation.findNavController(this, R.id.host_fragment)
        // binding.bottomNavigationView.setupWithNavController( navController)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}