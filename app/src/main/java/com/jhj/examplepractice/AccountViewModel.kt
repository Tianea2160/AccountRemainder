package com.jhj.examplepractice

import androidx.lifecycle.*
import com.jhj.examplepractice.database.Account
import com.jhj.examplepractice.database.AccountRepository
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: AccountRepository): ViewModel() {
    val allWords : LiveData<List<Account>> = repository.allWords.asLiveData()
    fun insert(account: Account) = viewModelScope.launch {
        repository.insert(account)
    }
}


class AccountViewModelFactory(private val repository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}