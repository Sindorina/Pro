package com.pro.network

import com.pro.utils.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit


object RetrofitFactory {

    private val BASE_URL = "https://www.baidu.com/"//根地址

    private val TIMEOUT: Long = 30//超时时间
    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    private val httpClient = OkHttpClient.Builder()
        // 添加通用的Header
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }.sslSocketFactory(
            SSLSocketFactoryUtils.createSSLSocketFactory()!!,
            SSLSocketFactoryUtils.createTrustAllManager()!!
        )
        .hostnameVerifier(SSLSocketFactoryUtils.TrustAllHostnameVerifier())
        /*
            这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
            出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
             */
        .addInterceptor(HttpLoggingInterceptor { message ->
            LogUtil.logE(
                "RetrofitFactory",
                "message-->$message"
            )
        }.setLevel(HttpLoggingInterceptor.Level.BASIC))
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    // 添加Gson转换器
    // 添加Retrofit到RxJava的转换器
    val instance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build()
        .create(RetrofitService::class.java)
}
