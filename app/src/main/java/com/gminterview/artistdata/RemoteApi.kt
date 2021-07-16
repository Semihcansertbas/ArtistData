package com.gminterview.artistdata

import com.gminterview.artistdata.model.ItunesResult
import com.gminterview.artistdata.util.Constants.BASE_URL
import com.gminterview.artistdata.util.Constants.MAX_RESULTS
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
This class will be used to access the remote server
It will fetch the api data and return it to the app
*/
interface RemoteApi {
    @GET("search")
    suspend fun getTracks(
        @Query("term") artistName: String,
        @Query("limit") limit: Int = MAX_RESULTS,
        @Query("media") mediaType: String = "music"
    ): Response<ItunesResult>

    companion object{
        operator fun invoke(): RemoteApi{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RemoteApi::class.java)
        }
    }
}