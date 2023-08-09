package com.example.ghusers.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ghusers.data.db.dao.RepositoryDao
import com.example.ghusers.data.db.dao.UserDao
import com.example.ghusers.data.model.Repository
import com.example.ghusers.data.model.RepositoryUser
import com.example.ghusers.data.model.User

@Database(version = 2, entities = [Repository::class, User::class, RepositoryUser::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun repositoryDao(): RepositoryDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "github_users")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}