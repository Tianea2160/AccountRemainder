package com.jhj.examplepractice.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import org.mindrot.jbcrypt.BCrypt

class AccountRepository(private val accountDao: AccountDao) {
    val allAccount: Flow<List<Account>> = accountDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(account: Account) {
        // 넣기 전에 암호화 해서 넣는다.
        // 암호화를 하는 이유는 해당 room 저장소가 다른 사람에게 해킹 당했을 때 안에 중요한 개인정보를 탈취당하지 않도록 하기 위해서
        accountDao.insert(account)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(account: Account) {
        accountDao.delete(account.name)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAccountByName(target : String):Flow<Account?>{
        return accountDao.getAccountByName(target)
    }
}