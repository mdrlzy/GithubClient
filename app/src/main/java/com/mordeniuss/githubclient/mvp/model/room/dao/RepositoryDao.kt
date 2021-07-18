package com.mordeniuss.githubclient.mvp.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mordeniuss.githubclient.mvp.model.entity.room.RoomRepository

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: RoomRepository)

    @Query("SELECT * FROM RoomRepository")
    suspend fun getAll(): List<RoomRepository>

    @Query("SELECT * FROM RoomRepository WHERE id = :id")
    suspend fun getById(id: Long): RoomRepository?
}