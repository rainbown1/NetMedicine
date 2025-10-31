package com.aristidevs.instadev.domain.usecase

import com.aristidevs.instadev.domain.entity.UserEntity
import com.aristidevs.instadev.domain.repository.AuthRepository
import javax.inject.Inject

class Login @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(user:String, password:String):UserEntity?{
        if(user.contains("@hotmail.com")){
            return null
        }
        val response = authRepository.doLogin(user, password)
        return response.random()
    }
}