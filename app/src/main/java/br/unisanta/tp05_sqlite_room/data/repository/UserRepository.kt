package br.unisanta.tp05_sqlite_room.data.repository

import androidx.lifecycle.LiveData
import br.unisanta.tp05_sqlite_room.data.dao.UserDao
import br.unisanta.tp05_sqlite_room.data.model.User


class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) = userDao.insert(user)
    suspend fun update(user: User) = userDao.update(user)
    suspend fun delete(user: User) = userDao.delete(user)
    suspend fun getByEmail(email: String) = userDao.getByEmail(email)
    suspend fun getById(id: Int) = userDao.getById(id)
    fun getAllLive(): LiveData<List<User>> = userDao.getAllLive()
}