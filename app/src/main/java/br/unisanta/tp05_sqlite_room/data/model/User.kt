package br.unisanta.tp05_sqlite_room.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var email: String,
    var password: String,
    var age: Int,
    var phone: String,
    var course: String
)
