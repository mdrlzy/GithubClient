package com.mordeniuss.githubclient.mvp.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mordeniuss.githubclient.mvp.model.entity.room.RoomUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: RoomUser)

    @Query("SELECT * FROM RoomUser WHERE id = :userId")
    suspend fun getById(userId: Long): RoomUser?
}