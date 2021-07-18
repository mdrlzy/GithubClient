package com.mordeniuss.githubclient.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomUser(
    @PrimaryKey
    val id: Long,
    val login: String,
    val avatar: String
)