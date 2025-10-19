package com.app.harigaji.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserProgressDetail(
    @PrimaryKey(autoGenerate = false)
val id: String,
val name: String?=null,
    val firstName: String?=null,
    val lastName: String?=null,
val email: String?=null,
val phone: String?=null,
val token: String?=null,
val baseUrl: String?=null,
val language: String?=null,
val profilePic: String?=null
)
