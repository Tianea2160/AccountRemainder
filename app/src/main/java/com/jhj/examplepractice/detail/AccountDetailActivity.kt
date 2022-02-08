package com.jhj.examplepractice.detail

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

        val detailAccountName:String? = intent.extras?.getString(DETAIL_ACCOUNT_NAME)
        val detailAccountId:String? = intent.extras?.getString(DETAIL_ACCOUNT_ID)
        val detailAccountPwd:String? = intent.extras?.getString(DETAIL_ACCOUNT_PWD)
        val detailAccountToken:String? = intent.extras?.getString(DETAIL_ACCOUNT_TOKEN)

        //저장소에서 해당 pk로 정보 불러오기
        val detailName = binding.detailAccountName
        val detailId = binding.detailAccountId
        val detailPwd = binding.detailAccountPwd
        val detailToken = binding.detailAccountToken

        var currentAccount:Account = Account("","","","")
        detailAccountName?.let{
            currentAccount = Account(it,
                detailAccountId?:"",
                detailAccountPwd?:"",
                detailAccountToken?:"")

            detailName.text = it
            detailId.text = currentAccount.id
            detailPwd.text = currentAccount.pwd
            detailToken.text = currentAccount.token
        }

        binding.deleteButton.setOnClickListener {
            accountDetailViewModel.delete(currentAccount)
            finish()
        }
    }
}