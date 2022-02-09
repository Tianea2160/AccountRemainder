package com.jhj.examplepractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jhj.examplepractice.add.NewAccountActivity
import com.jhj.examplepractice.database.Account
import com.jhj.examplepractice.detail.AccountDetailActivity

const val DETAIL_ACCOUNT_NAME:String = "account detail name"
const val DETAIL_ACCOUNT_ID:String = "account detail id"
const val DETAIL_ACCOUNT_PWD:String = "account detail pwd"
const val DETAIL_ACCOUNT_TOKEN:String = "account detail token"

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val accountViewModel: AccountViewModel by viewModels {
        AccountViewModelFactory((application as AccountsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = AccountListAdapter()

        adapter.setAccountClickListener( object : AccountListAdapter.AccountClickListener{
            override fun onClick(view: View, account: Account) {
                Log.d("SSS", "${account.name}번 리스트 선택")
                val intent = Intent(this@MainActivity, AccountDetailActivity::class.java)
                intent.putExtra("account", account)
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //life cycle dependency 에 있는 옵저버를 사용해야한다. 다른 옵저버는 사용하면 에러가 뜬다.
        accountViewModel.allWords.observe(this, Observer { accounts ->
            // Update the cached copy of the words in the adapter.
            accounts?.let { adapter.submitList(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewAccountActivity::class.java)
            startActivityForResult(intent,newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val name:String? =  data?.getStringExtra(NewAccountActivity.ACCOUNT_NAME)
            val id:String? =  data?.getStringExtra(NewAccountActivity.ACCOUNT_ID)
            val pwd:String? =  data?.getStringExtra(NewAccountActivity.ACCOUNT_PASSWORD)
            val token:String? =  data?.getStringExtra(NewAccountActivity.ACCOUNT_TOKEN)

            //intent에 정보를 넣기전에 무조건 string이 들어가도록 검사를 했다.
            name?.let{
                val account = Account(it, id?:"", pwd?:"", token?:"")
                accountViewModel.insert(account)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}