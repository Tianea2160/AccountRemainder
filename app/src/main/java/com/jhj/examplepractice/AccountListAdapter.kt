package com.jhj.examplepractice

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhj.examplepractice.AccountListAdapter.*
import com.jhj.examplepractice.database.Account
import com.jhj.examplepractice.detail.AccountDetailActivity

class AccountListAdapter : ListAdapter<Account, WordViewHolder>(WordsComparator()) {
    //메인 액티비티에서 클릭이벤트를 정의할 수 있도록 인터페이스 구현
    interface AccountClickListener{
        fun onClick(view : View, account:Account)
    }
    private lateinit var accountClickListener: AccountClickListener

    fun setAccountClickListener(accountClickListener: AccountClickListener){
        //밖에서 인자로 이벤트 동작을 입력 받는다 싱글 추상 메소드를 가진 인터페이스이므로 람다식을 이용해서 구현할 수 있다.
        this.accountClickListener = accountClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            accountClickListener.onClick(it, current)
        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val accountNameItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(account: Account?) {
            accountNameItemView.text = account?.name ?: " "
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }
    class WordsComparator : DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.id == newItem.id &&
                    oldItem.pwd == newItem.pwd &&
                    oldItem.token == newItem.token
        }
    }
}