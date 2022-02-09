package com.jhj.examplepractice.detail

import androidx.lifecycle.*
import com.jhj.examplepractice.database.Account
import com.jhj.examplepractice.database.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccountDetailViewModel(private val repository: AccountRepository): ViewModel() {
    //세부사항 클릭이 이루어질 때 할당
    fun getAccountByName(name: String): Account?{
        var account:Account? = null
        viewModelScope.launch {
            account = repository.getAccountByName(name).asLiveData().value
        }
        return account
    }

    fun delete(account:Account) = viewModelScope.launch { repository.delete(account) }
}

class AccountDetailViewModelFactory(private val repository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}