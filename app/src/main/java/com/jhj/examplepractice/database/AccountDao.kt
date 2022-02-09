package com.jhj.examplepractice.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountDao {

    @Query("SELECT * FROM account_table ORDER BY name ASC")
    fun getAlphabetizedWords(): Flow<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Account)

    @Query("DELETE FROM account_table;")
    suspend fun deleteAll()

    @Query("DELETE FROM account_table WHERE name = :name;")
    suspend fun delete(name:String)

    @Query("SELECT * FROM account_table WHERE name = :name;")
    fun getAccountByName(name:String): Flow<Account?>

}
