package com.mordeniuss.githubclient.mvp.model.datasource

import com.mordeniuss.githubclient.mvp.model.entity.Repository
import com.mordeniuss.githubclient.mvp.model.entity.User
import com.mordeniuss.githubclient.mvp.model.entity.room.RoomRepository
import com.mordeniuss.githubclient.mvp.model.entity.room.RoomUser
import com.mordeniuss.githubclient.mvp.model.room.dao.RepositoryDao
import com.mordeniuss.githubclient.mvp.model.room.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryLocalDataSource(
    val repositoryDao: RepositoryDao,
    val userDao: UserDao
) {
    suspend fun save(repository: Repository) = withContext(Dispatchers.IO) {
        Timber.d("saving $repository")
        val roomRepo = mapRepoToRoom(repository)
        val roomUser = mapUserToRoom(repository.owner)
        repositoryDao.insert(roomRepo)
        userDao.insert(roomUser)
    }

    suspend fun getById(id: Long): Repository? = withContext(Dispatchers.IO) {
        val repo = repositoryDao.getById(id)
        repo?.let {
            val user = mapUserFromRoom(userDao.getById(it.ownerId)!!)
            mapRepoFromRoom(it, user)
        }
    }

    suspend fun getAll(): List<Repository> = withContext(Dispatchers.IO) {
        val repos = repositoryDao.getAll().map { roomRepo ->
            val user = mapUserFromRoom(userDao.getById(roomRepo.ownerId)!!)
            mapRepoFromRoom(roomRepo, user)
        }
        Timber.d("get all $repos")
        repos
    }


    private fun mapRepoToRoom(repository: Repository): RoomRepository {
        return RoomRepository(repository.id, repository.name, repository.description, repository.owner.id, repository.link, repository.isDownloaded)
    }

    private fun mapUserToRoom(user: User): RoomUser {
        return RoomUser(user.id, user.login, user.avatar)
    }

    private fun mapRepoFromRoom(repository: RoomRepository, user: User): Repository {
        return Repository(repository.id, repository.name, repository.description, user, repository.link, repository.isDownloaded)
    }

    private fun mapUserFromRoom(user: RoomUser): User {
        return User(user.id, user.login, user.avatar)
    }
}