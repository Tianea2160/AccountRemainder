package com.jhj.examplepractice.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "account_table")
data class Account(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo
    val id:String,
    @ColumnInfo
    val pwd:String,
    @ColumnInfo
    val token:String,
) :Serializable