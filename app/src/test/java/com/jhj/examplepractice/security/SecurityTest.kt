package com.jhj.examplepractice.security

import org.jetbrains.annotations.TestOnly
import org.junit.Test


internal class SecurityTest{


    @Test
    fun 시큐리티_테스트(){
        val encoding = Security.encryptCBC("hello world!");
        println(encoding)

        val decoding = Security.decryptCBC(encoding)
        println(decoding)
    }
}