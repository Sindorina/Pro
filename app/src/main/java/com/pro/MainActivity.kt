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
        queryGoods()
    }

    private fun initDeviceInfo(){
        //进行设备信息采集
        //UserDeviceInfo.Device = Build.DEVICE
    }

    private fun queryGoods(){
        val fieldMap = mapOf(Pair("phone", UserDeviceInfo.phone),
            Pair("password", UserDeviceInfo.password)
        )
        //headerMap这个是公共的请求header  看下是不是所有除获取token的其他接口都一样的
        val headerMap = UserDeviceInfo.getHeaderMap()
        val observable = RetrofitFactory.instance.getToken(fieldMap,headerMap)
        ApiMethod.apiSubscribe(observable, object:BaseObserver<Any>(){
            override fun onHandleError(msg: String) {
                LogUtil.logE(TAG,"result错误->$msg")
            }

            override fun onHandleSuccess(t: JSONObject) {
                LogUtil.logE(TAG,"result->$t")
            }

            override fun onError(e: Throwable) {
            }
        })
    }

}
