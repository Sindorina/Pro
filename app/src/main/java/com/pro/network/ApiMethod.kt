package com.pro.network

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit

object ApiMethod {
    /**
     * 封装线程管理和订阅的过程
     */
    fun apiSubscribe(observable: Observable<ResponseBody>, observer: Observer<Any>) {
        var retryCount: Int = 0
        observable.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen {
                return@retryWhen it.flatMap {
                    if (++retryCount < 3) {
                        Observable.timer(1000, TimeUnit.SECONDS)
                    } else {
                        Observable.error(Throwable("重试失败!"))
                    }
                }
            }
            .subscribe(observer)
    }
}
