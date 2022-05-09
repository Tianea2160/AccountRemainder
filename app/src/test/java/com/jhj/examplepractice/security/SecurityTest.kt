package com.jhj.examplepractice.security

import SecurityUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.powermock.api.mockito.PowerMockito


@RunWith(JUnit4::class)
internal class SecurityTest {

    object Base64 {
        fun encodeToString(input: ByteArray?, flags: Int): String {
            return java.util.Base64.getEncoder().encodeToString(input)
        }

        fun decode(str: String?, flags: Int): ByteArray {
            return java.util.Base64.getDecoder().decode(str)
        } // add other methods if required...
    }

    @Test
    fun 시큐리티_테스트() {

        val securityUtil = SecurityUtil();


        val sent = "hello world";

        val encoded = securityUtil.encryptECB(sent);
        println(encoded);


        val decoded = securityUtil.decryptECB(encoded);
        println(decoded);
    }
}