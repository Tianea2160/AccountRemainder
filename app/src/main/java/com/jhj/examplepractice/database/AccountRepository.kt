package com.jhj.examplepractice.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

class AccountRepository(private val accountDao: AccountDao) {
    val allWords: Flow<List<Account>> = accountDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Account) {
        accountDao.insert(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(word: Account) {
        accountDao.delete(word.name)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAccountByName(target : String):Flow<Account?>{
        return accountDao.getAccountByName(target)
    }
}