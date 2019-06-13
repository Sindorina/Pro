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
        val headerMap = UserDeviceInfo.getHeaderMap()
        val observable = RetrofitFactory.instance.getToken(fieldMap,headerMap)
        ApiMethod.apiSubscribe(observable, object:BaseObserver<Any>(){
            override fun onHandleSuccess(t: JSONObject) {
                LogUtil.logE(TAG,"result->$t")
            }

            override fun onError(e: Throwable) {
            }
        })
    }

}
