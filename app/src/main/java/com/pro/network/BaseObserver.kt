package com.pro.network

import android.util.Log
import com.pro.utils.LogUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


abstract class BaseObserver<T> : Observer<T> {
    private var d: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        this.d = d
    }

    override fun onNext(value: T) {
        if (value == null) {
            onHandleError("empty response!")
        } else {
            try {
                if (value is Response<*>) {
                    val code = value.code()
                    LogUtil.logE(TAG, "code-->$code")
                    //http展厅码的处理
                    when (code) {
                        200 -> {
                            val bean = JSONObject(value.body().toString())
                            onHandleSuccess(bean)
                        }
                        4001 -> {//获取token
                            UserDeviceInfo.getToken()
                            retry()
                        }
                        else -> onHandleError("response error $code")
                    }
                } else {
                    onHandleError("response not ResponseBody type")
                }
            } catch (e: JSONException) {
                onHandleError("JSONException")
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
                onHandleError("IOException")
            }
        }
    }

    override fun onError(e: Throwable) {
        var needHandleError = true
        if (e is HttpException) {
            val errorCode = e.response().code()
            LogUtil.logE(TAG, "errorCode-->$errorCode")
            if (errorCode == 4001) {
                //获取token
                UserDeviceInfo.getToken()
                needHandleError = false
                retry()//重试
            }
            //测试获取token
            UserDeviceInfo.getTokenTest()
        }
        if (needHandleError) {
            onHandleError("{error:" + e.message + "}")
        }
    }

    override fun onComplete() {
        Log.e(TAG, "onComplete")
    }

    protected abstract fun onHandleSuccess(t: JSONObject)

    protected abstract fun onHandleError(msg: String)

    protected abstract fun retry()
    companion object {
        private val TAG = "BaseObserver"
    }
}
