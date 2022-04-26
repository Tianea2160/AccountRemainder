package com.jhj.examplepractice.detail

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.jhj.examplepractice.*
import com.jhj.examplepractice.database.Account
import com.jhj.examplepractice.databinding.ActivityAccountDetailBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class AccountDetailActivity : AppCompatActivity() {
    private val accountDetailViewModel by viewModels<AccountDetailViewModel> {
        AccountDetailViewModelFactory((application as AccountsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAccountDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //저장소에서 해당 pk로 정보 불러오기
        val detailName = binding.detailAccountName
        val detailId = binding.detailAccountId
        val detailPwd = binding.detailAccountPwd
        val detailToken = binding.detailAccountToken

        val currentAccount: Account? = intent.getSerializableExtra("account") as Account?
        if (currentAccount == null) {
            Log.d("SSS", "current account is null")
            finish()
        }

        //현재 데이터에 대한 처리
        detailName.text = currentAccount?.name
        detailId.text = currentAccount?.id
        detailPwd.text = currentAccount?.pwd
        detailToken.text = currentAccount?.token

        val clipboardManager: ClipboardManager =
            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        // 클립보드 복사를 위한 리스너
        binding.detailCopyButtonId.setOnClickListener {
            val clip = ClipData.newPlainText("data", detailId.text)
            clipboardManager.setPrimaryClip(clip)
            Log.i("copy", "copy id")
        }
        binding.detailCopyButtonPwd.setOnClickListener {
            val clip = ClipData.newPlainText("data", detailPwd.text)
            clipboardManager.setPrimaryClip(clip)
            Log.i("copy", "copy pwd")
        }
        binding.detailCopyButtonToken.setOnClickListener {
            val clip = ClipData.newPlainText("data", detailToken.text)
            clipboardManager.setPrimaryClip(clip)
            Log.i("copy", "copy token")
        }
        // 삭제 버튼을 위한 리스너
        binding.deleteButton.setOnClickListener {
            accountDetailViewModel.delete(currentAccount!!)
            finish()
            Log.i("delete", "delete account")
        }
    }
}