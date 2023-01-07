package com.yuan.order.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.yuan.order.R
import com.yuan.order.util.http.NetConstants
import com.yuan.order.util.http.NetUtils
import okhttp3.*
import java.io.IOException

class WelcomeActivity : BaseActivity() {

    private val TAG = "WelcomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val sp = getSharedPreferences("login_info", MODE_PRIVATE)
        val phone = sp.getString("phone", null)
        val password = sp.getString("password", null)
        if (phone == null || password == null) {
            Thread {
                Thread.sleep(1000)
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
            }.start()
        } else {
            asyncLogin(phone, password)
        }

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
                showToast(this@WelcomeActivity, "连接服务器失败")
            }

            override fun onResponse(call: Call, response: Response) {
                // 判断连接是否正常
                val json = response.body!!.string()

                if (!response.isSuccessful) {
                    Log.e(TAG, "登录请求失败$response....$json")
                    showToast(this@WelcomeActivity, "注册请求失败")
                    return
                }

                val receive = NetUtils.getCommonReceive(json)

                if (receive.status == "success") {
                    startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                    finish()
                } else {
                    val err = NetUtils.getErrorReceive(receive.data.toString())
                    showToast(this@WelcomeActivity, err.errMsg!!)
                }
            }
        })
    }
}
