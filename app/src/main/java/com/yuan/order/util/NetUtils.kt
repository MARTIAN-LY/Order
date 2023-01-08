package com.yuan.order.util

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

object NetUtils {

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    val gson = Gson()

    fun getCommonReceive(json: String): CommonReceive {
        return gson.fromJson(json, CommonReceive::class.java)
    }

    fun getErrorReceive(json: String): ErrorReceive {
        return gson.fromJson(json, ErrorReceive::class.java)
    }

    fun parseReceive(response: Response): CommonReceive {
        return CommonReceive("success", "")
    }
}