package com.yuan.order.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.yuan.order.R
import com.yuan.order.databinding.ActivityRegisterBinding
import com.yuan.order.util.InputUtils
import com.yuan.order.util.http.NetConstants
import com.yuan.order.util.http.NetUtils
import okhttp3.*
import java.io.IOException
import kotlin.concurrent.thread

class RegisterActivity : BaseActivity() {

    private val TAG = "RegisterActivity"
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check password
        binding.editPassword.doOnTextChanged { text, start, before, count ->
            if (!InputUtils.isPasswordValid(text.toString())) {
                binding.inputPassword.error = "不能少于6个字符(去除空格键后)"
            } else {
                binding.inputPassword.error = null
            }
        }

        binding.editPassConfirm.doOnTextChanged { text, start, before, count ->
            binding.inputPassConfirm.error = null
        }

        binding.btnRegister.setOnClickListener {

            showProgress()

            val typeSelect = binding.selectGroup.checkedRadioButtonId
            val schoolId = binding.editSchoolId.text.toString()
            val phone = binding.editPhone.text.toString()
            val alias = binding.editAlias.text.toString()
            val password = binding.editPassword.text.toString()
            val passConfirm = binding.editPassConfirm.text.toString()

            if (checkInput(schoolId, phone, alias, password, passConfirm)) {
                asyncRegister(typeSelect, schoolId, phone, alias, password)
            } else {
                Thread {
                    Thread.sleep(500)
                    hideProgress()
                }.start()
            }
        }

        binding.selectGroup.setOnCheckedChangeListener { radioGroup, id ->
            if (id == R.id.select_manager) {
                binding.inputManager.visibility = View.VISIBLE
                binding.inputStaff.visibility = View.GONE
            } else if (id == R.id.select_seller) {
                binding.inputManager.visibility = View.GONE
                binding.inputStaff.visibility = View.VISIBLE
            } else {
                binding.inputStaff.visibility = View.GONE
                binding.inputManager.visibility = View.GONE
            }
        }
    }


    private fun checkInput(
        schoolId: String,
        phone: String,
        alias: String,
        password: String,
        passConfirm: String
    ): Boolean {
        if (InputUtils.isEmpty(schoolId)
            || InputUtils.isEmpty(phone)
            || InputUtils.isEmpty(alias)
            || InputUtils.isEmpty(password)
            || InputUtils.isEmpty(passConfirm)
        ) {
            Toast.makeText(this@RegisterActivity, "输入不能为空", Toast.LENGTH_SHORT).show()
            return false
        }
        val p1 = password.trim()
        val p2 = passConfirm.trim()

        if (p1.length < 6) {
            Toast.makeText(this@RegisterActivity, "密码不符合要求", Toast.LENGTH_SHORT).show()
            return false
        }
        if (p1 != p2) {
            Toast.makeText(this@RegisterActivity, "两次密码不一致", Toast.LENGTH_SHORT).show()
            binding.inputPassConfirm.error = "两次密码不一致"
            return false
        }
        return true
    }

    private fun asyncRegister(
        typeSelect: Int,
        schoolId: String,
        phone: String,
        alias: String,
        password: String
    ) {
        val type = when (typeSelect) {
            R.id.select_customer -> "0"
            R.id.select_seller -> "1"
            R.id.select_manager -> "2"
            else -> "0"
        }
        val requestBody = FormBody.Builder()
            .add("type", type)
            .add("phone", phone)
            .add("school_id", schoolId)
            .add("alias", alias)
            .add("password", password)
            .build()

        //.header("application", "x-www-form-urlencoded")
        val request = Request.Builder()
            .url(NetConstants.BASE_URL + NetConstants.registerURL)
            .post(requestBody)
            .build()

        NetUtils.okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "连接服务器失败" + e.message)
                showToast(this@RegisterActivity, "连接服务器失败, 请重试！")
                hideProgress()
            }

            override fun onResponse(call: Call, response: Response) {

                val json = response.body!!.string()

                if (!response.isSuccessful) {
                    Log.e(TAG, "注册请求失败$response....$json")
                    showToast(this@RegisterActivity, "注册请求失败")
                    hideProgress()
                    return
                }

                val receive = NetUtils.getCommonReceive(json)

                if (receive.status == "success") {
                    showToast(this@RegisterActivity, "注册成功")
                    runOnUiThread {
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        intent.putExtra("phone", binding.editPhone.text.toString())
                        intent.putExtra("password", binding.editPassword.text.toString())
                        intent.putExtra("autoLogin", true)
                        startActivity(intent)
                    }
                } else {
                    val err = NetUtils.getErrorReceive(receive.data.toString())
                    showToast(this@RegisterActivity, err.errMsg!!)
                    hideProgress()
                }
            }
        })
    }

    private fun showProgress() {
        runOnUiThread {
            binding.btnRegister.visibility = View.GONE
            binding.registerProgress.visibility = View.VISIBLE
        }
    }

    private fun hideProgress() {
        runOnUiThread {
            binding.btnRegister.visibility = View.VISIBLE
            binding.registerProgress.visibility = View.GONE
        }
    }
}
