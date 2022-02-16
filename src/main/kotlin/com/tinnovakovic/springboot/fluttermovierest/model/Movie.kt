package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*


@Entity
@Table(
    name = "movie",
    uniqueConstraints = [UniqueConstraint(name = "unique_movie_constraints", columnNames = ["mdb_id"])]
)
data class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int,
    @Column(name = "mdb_id", nullable = false, columnDefinition = "TEXT")
    val mDbId: String,
    @Column(name = "poster_path", nullable = false, columnDefinition = "TEXT")
    val posterPath: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "movie_detail_id", referencedColumnName = "id")
    var movieDetail: MovieDetail,
    @ManyToMany(mappedBy = "favMovies")
    val appUsers: Set<AppUser>
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

        other as Movie

        if (id != other.id) return false
        if (mDbId != other.mDbId) return false
        if (posterPath != other.posterPath) return false
        if (movieDetail != other.movieDetail) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + mDbId.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + movieDetail.hashCode()
        return result
    }
}