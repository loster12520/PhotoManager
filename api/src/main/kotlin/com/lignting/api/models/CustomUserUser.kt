package com.lignting.api.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.beans.BeanUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User


class CustomUser(
    val user: CustomUserModel,
    val grantedAuthorities: Collection<GrantedAuthority>,
) : User(user.userName, user.password, grantedAuthorities)

@Entity
data class CustomUserModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var userName: String? = null,
    var password: String? = null,
)

data class UserDTO(
    var userName: String = "",
    var password: String = "",
)

fun CustomUserModel.toDTO(): UserDTO {
    val userDTO = UserDTO()
    BeanUtils.copyProperties(this, userDTO)
    return userDTO
}

fun UserDTO.toEntity(): CustomUserModel {
    val customUserUser = CustomUserModel()
    BeanUtils.copyProperties(this, customUserUser)
    return customUserUser
}