package com.yuan.order.util

import android.util.Base64
import kotlin.Throws
import com.yuan.order.home.model.Dish
import com.yuan.order.util.InputUtils
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.regex.Pattern

object InputUtils {

    // 校验密码不少于6位
    fun isPasswordValid(password: String?): Boolean {
        return password != null && password.replace(" ".toRegex(), "").length > 5
    }

    fun isEmpty(s: String?): Boolean {
        return s == null || s.trim { it <= ' ' }.length == 0
    }

    fun equals(a: String?, b: String?): Boolean {
        return if (a == null) {
            b == null
        } else a == b
    }

    /**
     * MD5加密+BASE64编码
     */
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun encodeByMd5(str: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        // 注意这里是 Base64.NO_WRAP，不能用 Base64.DEFAULT，否则结尾会带一个 \n
        return String(
            Base64.encode(
                md5.digest(str.toByteArray(StandardCharsets.UTF_8)),
                Base64.NO_WRAP
            )
        )
    }


    const val CANTEEN1 = 0.toShort()
    const val CANTEEN2 = 1.toShort()
    const val CANTEEN3 = 2.toShort()
    const val CANTEEN4 = 3.toShort()
    val list: MutableList<Dish> = ArrayList()

    init {
        list.add(Dish("鲜肉包", "面夫子", "皮薄馅多", CANTEEN1, 2.toShort()))
        list.add(Dish("牛肉饭", "F+牛肉饭", "超多牛肉，大满足", CANTEEN1, 20.toShort()))
        list.add(Dish("经典套餐", "大众自选", "实惠健康", CANTEEN1, 13.toShort()))
        list.add(Dish("奶茶", "奶茶店", "好喝", CANTEEN1, 8.toShort()))
        list.add(Dish("韭菜鸡蛋饺子", "饺子馆", "全是韭菜，没有鸡蛋", CANTEEN1, 8.toShort()))
        list.add(Dish("番茄鸡蛋面", "面馆", "汤面", CANTEEN2, 8.toShort()))
        list.add(Dish("鸡腿饭", "粤式烧腊", "经典粤式烧腊，好吃", CANTEEN3, 15.toShort()))
        list.add(Dish("馄饨", "馄饨店", "好吃的小馄饨", CANTEEN3, 10.toShort()))
        list.add(Dish("鸡蛋灌饼", "正宗鸡蛋灌饼", "一般般的鸡蛋灌饼", CANTEEN4, 10.toShort()))
        list.add(Dish("牛肉拉面", "兰州牛肉拉面", "肉不多", CANTEEN4, 12.toShort()))
        list.add(Dish("油饼", "砂锅饭", "食不食油饼", CANTEEN4, 4.toShort()))
    }
}