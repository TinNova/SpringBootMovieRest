package com.tinnovakovic.springboot.fluttermovierest

import com.tinnovakovic.springboot.fluttermovierest.model.*
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class FluttermovierestApplication {
    @Bean
    fun init(userService: UserService) = CommandLineRunner {
        // save Roles (Don't worry about id = -1, it is smart enough to auto assign a unique id)
        userService.saveRole(Role(id = -1, name = ROLE_USER, emptySet()))
        userService.saveRole(Role(id = -1, name = ROLE_MANAGER, emptySet()))
        userService.saveRole(Role(id = -1, name = ROLE_ADMIN, emptySet()))
        userService.saveRole(Role(id = -1, name = ROLE_SUPER_ADMIN, emptySet()))

        userService.saveUser(
            RestAppUser(
                id = -1,
                username = "Tin",
                email = "tin@tin.com",
                password = "123",
                movies = emptySet(),
                actors = emptySet(),
                roles = emptySet()
            )
        )
        userService.saveUser(
            RestAppUser(
                id = -1,
                username = "Goran",
                email = "goran@goran.com",
                password = "123",
                movies = emptySet(),
                actors = emptySet(),
                roles = emptySet()
            )
        )
        userService.saveUser(
            RestAppUser(
                id = -1,
                username = "Leandros",
                email = "leandros@leandros.com",
                password = "123",
                movies = emptySet(),
                actors = emptySet(),
                roles = emptySet()
            )
        )
        userService.saveUser(
            RestAppUser(
                id = -1,
                username = "Mama",
                email = "mama@mama.com",
                password = "123",
                movies = emptySet(),
                actors = emptySet(),
                roles = emptySet()
            )
        )

        userService.addRoleToUser("tin@tin.com", ROLE_SUPER_ADMIN)
        userService.addRoleToUser("goran@goran.com", ROLE_ADMIN)
        userService.addRoleToUser("leandros@leandros.com", ROLE_MANAGER)
        userService.addRoleToUser("mama@mama.com", ROLE_USER)
        userService.addRoleToUser("mama@mama.com", ROLE_MANAGER)
        userService.addRoleToUser("mama@mama.com", ROLE_ADMIN)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}

fun main(args: Array<String>) {
    runApplication<FluttermovierestApplication>(*args)
}
