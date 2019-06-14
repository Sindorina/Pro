package com.pro.network


import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    //获取Token
    @FormUrlEncoded
    @POST("api/v1/token")
    fun getToken(
        @FieldMap fields:Map<String,String>,//参数的map
        @HeaderMap headers:Map<String,String>//header的map
    ): Observable<ResponseBody>

    //查询指定商品是否存在
    @FormUrlEncoded
    @POST("api/goods/exists")
    fun queryGoodExists(
        @FieldMap fields:Map<String,String>,//参数的map
        @HeaderMap headers:Map<String,String>//header的map
    ): Observable<Response<Any>>


    //测试网络接口
    @GET("/")
    fun getTokenTest(): Observable<ResponseBody>

    //测试接口
    @GET("top250")
    fun test(@Query("start") start :Int, @Query("count") count :Int): Observable<Response<Any>>
}
