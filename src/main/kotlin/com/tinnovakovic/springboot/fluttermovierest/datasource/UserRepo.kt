package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepo: JpaRepository<AppUser, Int> {

    @Query(value = "SELECT * FROM USER WHERE email = ?1", nativeQuery = true)
    fun retrieveUserByEmail(emailAddress: String): Optional<AppUser>
}