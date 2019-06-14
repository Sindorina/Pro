package com.pro.network

import com.pro.utils.LogUtil
import org.json.JSONObject

object UserDeviceInfo {
    //header参数
    private var Device:String = ""
    private var Terrace:String = ""
    private var SystemEdition:String = ""
    private var Application:String = ""
    private var AccessToken:String = ""
    //获取token时的参数
    var phone:String = ""
    var password:String = ""

    private val commonHeaderMap = mutableMapOf<String,String>()
    fun getHeaderMap():MutableMap<String,String>{
        if (commonHeaderMap.isEmpty()){
            commonHeaderMap["Device"] = Device
            commonHeaderMap["Terrace"] = Terrace
            commonHeaderMap["SystemEdition"] = SystemEdition
            commonHeaderMap["Application"] = Application
            commonHeaderMap["AccessToken"] = AccessToken
        }
        return commonHeaderMap
    }

    fun getToken(){
        val fieldMap = mapOf(Pair("phone", phone),
            Pair("password", password)
        )
        val headerMap = mapOf(Pair("Device", Device),
            Pair("Terrace", Terrace),
            Pair("SystemEdition", SystemEdition),
            Pair("Application", Application)
        )
        val observable = RetrofitFactory.instance.getToken(fieldMap,headerMap)
        ApiMethod.apiSubscribe(observable,observer)
    }
    private val observer = object :BaseObserver<Any>(){
        override fun onHandleError(msg: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onHandleSuccess(t: JSONObject) {
            val data:JSONObject = t.optJSONObject("data")
            val newToken = data.optString("token")
            if (newToken != ""){
                AccessToken = newToken
                commonHeaderMap["AccessToken"] = AccessToken
            }
        }

        override fun onError(e: Throwable) {
            LogUtil.logE("UserDeviceInfo","token获取失败!")
        }
    }

    fun getTokenTest(){
        val testObserverable = RetrofitFactory.instance.getTokenTest()
        ApiMethod.apiSubscribe(testObserverable,testObserver)
    }
    private val testObserver = object :BaseObserver<Any>(){
        override fun onHandleError(msg: String) {
            LogUtil.logE("UserDeviceInfo","获取测试token错误-->$msg")
        }

        override fun onHandleSuccess(t: JSONObject) {
            LogUtil.logE("UserDeviceInfo","获取测试token-->$t")
        }

    }

}