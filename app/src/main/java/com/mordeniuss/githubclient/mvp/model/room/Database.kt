package com.mordeniuss.githubclient.mvp.model.room

import androidx.room.RoomDatabase
import com.mordeniuss.githubclient.mvp.model.entity.room.RoomRepository
import com.mordeniuss.githubclient.mvp.model.entity.room.RoomUser
import com.mordeniuss.githubclient.mvp.model.room.dao.RepositoryDao
import com.mordeniuss.githubclient.mvp.model.room.dao.UserDao


@androidx.room.Database(
    entities = [
        RoomRepository::class,
        RoomUser::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
    abstract fun userDao(): UserDao

    companion object {
        const val DB_NAME = "database.db"
    }
}