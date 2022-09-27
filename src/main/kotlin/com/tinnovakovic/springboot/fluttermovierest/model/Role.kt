package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "role",
    uniqueConstraints = [UniqueConstraint(name = "unique_role_constraints", columnNames = ["name"])]
)
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int,
    @Column(name = "name")
    val name: String,
    @ManyToMany(mappedBy = "roles")
    val appUser: Set<AppUser>
) {
    /**
     * This is the fix for the recursion that happens between the ManyToMany relationship between AppUser & Role
     *
     * Remember - You can remove the following methods in either Movie or AppUser for the same result
     * Here the appUsers field was removed (but you could have also removed movies from the AppUser Data Class
     * It doesn't matter from which side you do it (AppUser or Movie)
     * */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

const val ROLE_USER = "ROLE_USER"
const val ROLE_MANAGER = "ROLE_MANAGER"
const val ROLE_ADMIN = "ROLE_ADMIN"
const val ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN"

