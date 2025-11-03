package com.aristidevs.instadev.domain.repository

import com.aristidevs.instadev.domain.entity.UserEntity

interface AuthRepository {
    suspend fun doLogin(user:String, password:String):List<UserEntity>
}