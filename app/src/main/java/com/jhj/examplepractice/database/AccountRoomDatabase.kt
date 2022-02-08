package com.jhj.examplepractice.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Account::class], version = 1, exportSchema = false)
abstract class AccountRoomDatabase:RoomDatabase(){
    abstract fun accountDao():AccountDao;

    private class AccountDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.accountDao())
                }
            }
        }

        suspend fun populateDatabase(accountDao: AccountDao) {
            // Delete all content here.
            accountDao.deleteAll()

            // Add sample words.
            var account = Account("Hello", "id1", "pwd1", "token1")
            accountDao.insert(account)
            account = Account("world", "id2", "pwd2", "token2")
            accountDao.insert(account)
        }
    }

    companion object{
        @Volatile
        private var INSTANCE:AccountRoomDatabase? = null

        fun getDatabase(context:Context, scope: CoroutineScope):AccountRoomDatabase{

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountRoomDatabase::class.java,
                    "account_database"
                ).addCallback(AccountDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
