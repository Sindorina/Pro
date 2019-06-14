package com.pro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pro.network.ApiMethod
import com.pro.network.BaseObserver
import com.pro.network.RetrofitFactory
import com.pro.network.UserDeviceInfo
import com.pro.utils.LogUtil
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val TAG  = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //网络框框测试
        initDeviceInfo()
        /**
         * 直接请求对应地址  如果返回4001说明 token过期
         * 在BaseObserver中, 会自动去请求一次token
         * @sample BaseObserver.onError  链接
         * 一秒一次总共3次
         */
        test()
    }

    private fun initDeviceInfo(){
        //进行设备信息采集
        //UserDeviceInfo.Device = Build.DEVICE
    }

    private fun queryGoods(){
        val fieldMap = mapOf(Pair("id", "111"),
            Pair("name", "pair")
        )
        //headerMap这个是公共的请求header  看下是不是所有除获取token的其他接口都一样的
        val headerMap = UserDeviceInfo.getHeaderMap()
        val observable = RetrofitFactory.instance.queryGoodExists(fieldMap,headerMap)
        ApiMethod.apiSubscribe2(observable, object:BaseObserver<Any>(){
            override fun retry() {
                //重新请求一次本次接口
            }

            override fun onHandleError(msg: String) {
                LogUtil.logE(TAG,"result错误->$msg")
            }

            override fun onHandleSuccess(t: JSONObject) {
                LogUtil.logE(TAG,"result->$t")
            }
        })
    }

    private fun test(){
        val observable = RetrofitFactory.instance.test(0, 10)
        ApiMethod.apiSubscribe2(observable,object :BaseObserver<Any>(){
            override fun retry() {

            }

            override fun onHandleError(msg: String) {
                LogUtil.logE(TAG,"result错误->$msg")
            }

            override fun onHandleSuccess(t: JSONObject) {
                LogUtil.logE(TAG,"result->$t")
            }
        })
    }
}
