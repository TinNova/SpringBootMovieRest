package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.User
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockUserDataSourceImpl : UserDataSource {

    val users = mutableListOf(
        User(1, "tin", "tin@gmail.com"),
        User(2, "goran", "goran@gmail.com"),
    )

    override fun retrieveUsers(): List<User> {
        return users
    }

    override fun retrieveUser(email: String): User {
        return users.firstOrNull { it.email == email }
            ?: throw NoSuchElementException("Could not find a user with an email of $email.")
    }

    override fun createUser(user: User): User {
        if (users.any { it.email == user.email }) {
            throw IllegalArgumentException("A user with the 'email' ${user.email} already exists")
        }
        users.add(user)
        return user
    }

    override fun updateUser(user: User): User {
        val currentUser = users.firstOrNull { it.email == user.email }
            ?: throw NoSuchElementException("Could not find a user with an 'email' of ${user.email}.")

        val currentUserIndex = users.indexOf(currentUser)
        users.set(currentUserIndex, user)
        return user
    }

    override fun deleteUser(email: String) {
        val currentUser = users.firstOrNull { it.email == email }
            ?: throw NoSuchElementException("Could not find a user with an 'email' of ${email}.")

        users.remove(currentUser)
    }
}