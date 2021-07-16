package com.gminterview.artistdata.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TestingUtilTest{
    @Test
    fun `empty text return true`(){
        val result = TestingUtil.checkStringValue("")
        assertThat(result).isTrue()
    }

    @Test
    fun `valid search text return false`(){
        val result = TestingUtil.checkStringValue("Taylor Swift")
        assertThat(result).isFalse()
    }
}