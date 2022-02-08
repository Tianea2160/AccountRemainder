package com.jhj.examplepractice

import android.app.Application
import androidx.room.RoomDatabase
import com.jhj.examplepractice.database.AccountRepository
import com.jhj.examplepractice.database.AccountRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AccountsApplication:Application(){

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AccountRoomDatabase.getDatabase(this, applicationScope)}

    val repository by lazy { AccountRepository(database.accountDao()) }

}