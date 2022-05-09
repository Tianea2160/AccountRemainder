import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class SecurityUtil {

    private constructor()

    companion object {
        const val SECRET_KEY = "ABCDEFGH12345678"
        const val SECRET_IV = "1234567812345678"

        private var instance: SecurityUtil? = null
        fun getInstance(): SecurityUtil {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) { //Double-Checked Locking
                        instance = SecurityUtil()
                    }
                }
            }
            return this.instance!!// 무조건 반환이 있다는 것이 보장됨
        }

    }

    fun encryptECB(rowPwd: String): String {
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES") /// 키
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING") //싸이퍼
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        // 암호화/복호화 모드
        val ciphertext = cipher.doFinal(rowPwd.toByteArray())
        val encodedByte = Base64.encode(ciphertext, Base64.DEFAULT)
        return String(encodedByte)
    }

    /** * ECB 복호화 */
    fun decryptECB(encodedPwd: String): String {
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        var decodedByte: ByteArray = Base64.decode(encodedPwd, Base64.DEFAULT)
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val output = cipher.doFinal(decodedByte)
        return String(output)
    }
}