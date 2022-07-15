package com.example.android.borutoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.borutoapp.data.local.dao.HeroDao
import com.example.android.borutoapp.data.local.dao.HeroRemoteKeysDao
import com.example.android.borutoapp.domain.model.entity.HeroEntity
import com.example.android.borutoapp.domain.model.entity.HeroRemoteKeys

@Database(entities = [HeroEntity::class, HeroRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase(): RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): BorutoDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, BorutoDatabase::class.java)
            } else {
                Room.databaseBuilder(context, BorutoDatabase::class.java, "test_database.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract val heroDao: HeroDao
    abstract val heroRemoteKeysDao: HeroRemoteKeysDao
}