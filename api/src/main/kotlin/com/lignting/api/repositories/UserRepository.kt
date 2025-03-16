package com.lignting.api.repositories

import com.lignting.api.models.CustomUserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<CustomUserModel, Int> {
    fun getUserByUserName(username: String): Optional<CustomUserModel>
}