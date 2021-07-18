package com.mordeniuss.githubclient.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomRepository(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String?,
    val ownerId: Long,
    val link: String,
    val isDownloaded: Boolean
)