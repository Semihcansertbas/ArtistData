package com.gminterview.artistdata


import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    private lateinit var api: RemoteApi

    @Before
    fun setup() {
        api = RemoteApi.invoke()
    }

    @Test
    fun ifConnectedToInternet_apiShouldReturnTrue() {
        val result: Boolean
        runBlocking {
            coroutineScope {
                val call = api.getTracks("Drake", 1)
                result = call.isSuccessful

            }
        }
        assertThat(result).isTrue()
    }

    @Test
    fun responseBodyNull_shouldReturnFalse(){
        val result: Boolean
        runBlocking {
            coroutineScope {
                val call = api.getTracks("Taylor Swift")
                result = call.body() == null
            }
        }
        assertThat(result).isFalse()
    }

    @Test
    fun defaultResultSizeShouldEqualTo100(){
        val result: Int
        runBlocking {
            coroutineScope {
                val call = api.getTracks("Taylor Swift")
                result = call.body()!!.resultCount
            }
        }
        assertThat(result).isEqualTo(100)
    }
}