package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "actor",
    uniqueConstraints = [UniqueConstraint(name = "unique_actor_constraints", columnNames = ["actorMdb_id"])]
)
data class Actor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int,
    @Column(name = "actorMdb_id", nullable = false, columnDefinition = "TEXT")
    val actorMdbId: String,
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    val name: String, // GetDetail
    @Column(name = "profile_path", nullable = false, columnDefinition = "TEXT")
    val profilePath: String, // GetDetail
    @Column(name = "biography", nullable = false, columnDefinition = "TEXT")
    val biography: String, // GetDetail
    @ManyToMany(mappedBy = "actors")
    val movieDetails: Set<MovieDetail>,
    @ManyToMany(mappedBy = "favActors")
    val appUsers: Set<AppUser>
//    val images: List<String>, // GetImages
) {
    /**
     * This is the fix for the recursion that happens between the ManyToMany relationship between Movie & AppUser
     *
     * Remember - You can remove the following methods in either Movie or AppUser for the same result
     * Here the appUsers field was removed (but you could have also removed movies from the AppUser Data Class
     * It doesn't matter from which side you do it (AppUser or Movie)
     * */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Actor

        if (id != other.id) return false
        if (actorMdbId != other.actorMdbId) return false
        if (name != other.name) return false
        if (profilePath != other.profilePath) return false
        if (biography != other.biography) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + actorMdbId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + profilePath.hashCode()
        result = 31 * result + biography.hashCode()
        return result
    }
}
