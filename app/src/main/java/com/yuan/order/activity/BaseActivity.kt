package com.yuan.order.activity

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    protected fun showToast(context: Context, msg: String) {
        runOnUiThread {
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }
    }

}