package com.pro.utils

import android.util.Log

/**
 * Created by Administrator on 2018/6/26
 * 邮箱 18780569202@163.com
 */
object LogUtil {

    private val VERBOSE = 1
    private val DEBUG = 2
    private val INFO = 3
    private val WARN = 4
    private val ERROR = 5
    private val RELEASE = 6
    private val CONTROL = VERBOSE//测试使用 VERBOSE 正式使用 RELEASE
    fun logV(TAG: String, result: String) {
        if (CONTROL <= VERBOSE) {
            Log.v(TAG, result)
        }
    }

    fun logI(TAG: String, result: String) {
        if (CONTROL <= VERBOSE) {
            Log.i(TAG, result)
        }
    }

    fun logD(TAG: String, result: String) {
        if (CONTROL <= VERBOSE) {
            Log.d(TAG, result)
        }
    }

    fun logW(TAG: String, result: String) {
        if (CONTROL <= VERBOSE) {
            Log.w(TAG, result)
        }
    }

    fun logE(TAG: String, result: String) {
        if (CONTROL <= VERBOSE) {
            Log.e(TAG, result)
        }
    }
}
