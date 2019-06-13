package com.pro.network

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
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
                if (value is ResponseBody) {
                    val bean = JSONObject((value as ResponseBody).string())
                    onHandleSuccess(bean)
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
        if (e is HttpException){
            val errorCode = e.response().code()
            if (errorCode == 4001){
                needHandleError = false
            }
        }
        if (needHandleError){
            onHandleError("{error:" + e.message + "}")
        }
    }

    override fun onComplete() {
        Log.e(TAG, "onComplete")
    }

    protected abstract fun onHandleSuccess(t: JSONObject)

    protected fun onHandleError(msg: String) {

    }

    companion object {
        private val TAG = "BaseObserver"
    }

}
