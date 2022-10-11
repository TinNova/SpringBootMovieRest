package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo: JpaRepository<AppUser, Int> {

    @Query(value = "SELECT * FROM APP_USER WHERE email = ?1", nativeQuery = true)
    fun findByEmail(emailAddress: String): Optional<AppUser>

    @Query(value = "SELECT * FROM APP_USER WHERE username = ?1", nativeQuery = true)
    fun findByUsername(username: String): Optional<AppUser>
}