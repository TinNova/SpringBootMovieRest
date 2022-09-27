package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.model.Role
import org.springframework.data.jpa.repository.JpaRepository

// This is different to the other Repo's, This follow the setup in this tutorial:
// https://www.youtube.com/watch?v=VVn9OG9nfH0 at time block 17:00
interface RoleRepo : JpaRepository<Role, Int> {

    fun findByName(name: String): Role

}
