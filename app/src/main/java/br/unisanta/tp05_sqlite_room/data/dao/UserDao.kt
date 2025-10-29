package br.unisanta.tp05_sqlite_room.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.unisanta.tp05_sqlite_room.data.model.User


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long


    @Update
    suspend fun update(user: User)


    @Delete
    suspend fun delete(user: User)


    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): User?


    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): User?


    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllLive(): LiveData<List<User>>
}