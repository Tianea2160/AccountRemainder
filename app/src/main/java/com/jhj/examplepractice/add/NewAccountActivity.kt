package com.jhj.examplepractice.add

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.jhj.examplepractice.R

class NewAccountActivity : AppCompatActivity() {

    private lateinit var editAccountName: EditText
    private lateinit var editAccountId: EditText
    private lateinit var editAccountPwd: EditText
    private lateinit var editAccountToken: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)
        //사용자로 부터 입력값 받기 위한 준비
        editAccountName = findViewById(R.id.edit_account_name)
        editAccountId = findViewById(R.id.edit_login_id)
        editAccountPwd = findViewById(R.id.edit_login_pwd)
        editAccountToken = findViewById(R.id.edit_login_token)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editAccountName.text)) {
                //이름은 반드시 만들어 줘야한다.
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editAccountName.text.toString()
                val id:String = if(TextUtils.isEmpty(editAccountId.text)) ""
                                else editAccountId.text.toString()

                val pwd:String = if(TextUtils.isEmpty(editAccountPwd.text)) ""
                                else editAccountPwd.text.toString()

                val token:String = if(TextUtils.isEmpty(editAccountToken.text)) ""
                                else editAccountToken.text.toString()


                replyIntent.putExtra(ACCOUNT_NAME, name)
                replyIntent.putExtra(ACCOUNT_ID, id)
                replyIntent.putExtra(ACCOUNT_PASSWORD, pwd)
                replyIntent.putExtra(ACCOUNT_TOKEN, token)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val ACCOUNT_NAME = "account name"
        const val ACCOUNT_ID = "account id"
        const val ACCOUNT_PASSWORD = "account pwd"
        const val ACCOUNT_TOKEN = "account token"
    }
}