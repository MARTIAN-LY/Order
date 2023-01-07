package com.yuan.order.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.yuan.order.R
import com.yuan.order.databinding.ActivityLoginBinding
import com.yuan.order.util.InputUtils
import com.yuan.order.util.http.NetConstants
import com.yuan.order.util.http.NetUtils
import okhttp3.*
import java.io.IOException

class LoginActivity : BaseActivity() {

    private val TAG = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 输入框提示信息
        binding.loginEditPhone.doOnTextChanged { text, start, before, count ->
            binding.loginInputPhone.error = null
        }

        binding.loginEditPhone.doOnTextChanged { text, start, before, count ->
            binding.loginInputPhone.error = null
        }

        // login button
        binding.buttonLogin.setOnClickListener {

            showProgress()

            val phone = binding.loginEditPhone.text.toString()
            val password = binding.loginEditPassword.text.toString()

            if (checkInput(phone, password)) {
                asyncLogin(phone, password)
            } else {
                Thread {
                    Thread.sleep(500)
                    hideProgress()
                }.start()
            }
        }

        // forget password
        binding.textForget.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }

        // register
        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent!!.getBooleanExtra("autoLogin", false)) {
            val phone = intent.getStringExtra("phone")
            val password = intent.getStringExtra("password")

            if (!(InputUtils.isEmpty(phone) || InputUtils.isEmpty(password))) {
                binding.loginEditPhone.setText(phone)
                binding.loginEditPassword.setText(password)
                asyncLogin(phone!!, password!!)
            }
        }
    }


    private fun checkInput(phone: String, password: String): Boolean {
        if (InputUtils.isEmpty(phone)) {
            binding.loginInputPhone.error = "手机号不能为空"
            Toast.makeText(this@LoginActivity, "输入不能为空", Toast.LENGTH_SHORT).show()
            return false
        }
        if (InputUtils.isEmpty(password)) {
            binding.loginInputPassword.error = "密码不能为空"
            Toast.makeText(this@LoginActivity, "输入不能为空", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun asyncLogin(phone: String, password: String) {
        // OkHttp 异步 post 请求, post 请求需要 requestBody
        val requestBody = FormBody.Builder()
            .add("phone", phone)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(NetConstants.BASE_URL + NetConstants.loginURL)
            .post(requestBody)
            .build()

        NetUtils.okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "连接服务器失败" + e.message)
                showToast(this@LoginActivity, "连接服务器失败")
                hideProgress()
            }

            override fun onResponse(call: Call, response: Response) {
                // 判断连接是否正常
                val json = response.body!!.string()

                if (!response.isSuccessful) {
                    Log.e(TAG, "登录请求失败$response....$json")
                    showToast(this@LoginActivity, "注册请求失败")
                    hideProgress()
                    return
                }

                val receive = NetUtils.getCommonReceive(json)

                if (receive.status == "success") {

                    val type = receive.data.toString()
                    val cache = getSharedPreferences("login_info", MODE_PRIVATE)
                    val editor = cache.edit()
                    editor.putString("phone", phone)
                    editor.putString("password", password)
                    editor.putString("type", type)
                    editor.apply()

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    val err = NetUtils.getErrorReceive(receive.data.toString())
                    showToast(this@LoginActivity, err.errMsg!!)
                    hideProgress()
                }
            }
        })
    }

    private fun showProgress() {
        runOnUiThread {
            binding.buttonLogin.visibility = View.GONE
            binding.loginProgress.visibility = View.VISIBLE
        }
    }

    private fun hideProgress() {
        runOnUiThread {
            binding.buttonLogin.visibility = View.VISIBLE
            binding.loginProgress.visibility = View.GONE
        }
    }
}
