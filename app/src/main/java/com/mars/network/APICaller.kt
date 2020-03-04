package com.mars.network

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface APICaller {

//    @FormUrlEncoded
//    @POST("{endPoint}")
//    operator fun invoke(@Path("endPoint") endPoint: String, @Body request: RequestBody):
//            Observable<Response<String>>
//
//    @POST("{endPoint}")
//    operator fun invoke(@Path("endPoint") endPoint: String):
//            Observable<Response<String>>
//
//    //Addiing new interface for file download
//    @POST("content")
//    @Streaming
//    fun download(@Body requestBody: RequestBody): Observable<Response<ResponseBody>>

    @GET("{endPoint}")
    operator fun get(@Path("endPoint") endPoint: String):
            Observable<Response<String>>

    @GET("{endPoint}")
    operator fun get(@Path("endPoint") endPoint: String, @Body request: RequestBody):
            Observable<Response<String>>

//    @POST("{endPoint}")
//    fun post(@Path("endPoint") endPoint: String, @Body request: RequestBody):
//            Observable<Response<String>>
//
//    @POST("{endPoint}")
//    fun post(@Path("endPoint") endPoint: String):
//            Observable<Response<String>>

}
