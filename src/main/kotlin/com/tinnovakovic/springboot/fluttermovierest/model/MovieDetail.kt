package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "movie_detail",
    uniqueConstraints = [UniqueConstraint(name = "unique_movieDetail_constraints", columnNames = ["mdb_id"])]
)
data class MovieDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,
    @Column(name = "mdb_id")
    val mDbId: String = "",
    @Column(name = "title")
    val title: String,
    @Column(name = "overview", length = 1000)
    val overview: String,
    @Column(name = "poster_path")
    val posterPath: String = "",
    @Column(name = "backdrop_path")
    val backdropPath: String,
    @Column(name = "directors")
    val directors: String,
    @Column(name = "popularity")
    val popularity: Double,
    @Column(name = "release_date")
    val releaseDate: String,
    @Column(name = "revenue")
    val revenue: Double,
    @Column(name = "runtime")
    val runtime: String,
    @Column(name = "tagline")
    val tagline: String,
    @Column(name = "vote_average")
    val voteAverage: Double,
    @Column(name = "vote_count")
    val voteCount: Int,
    @Column(name = "is_favourite")
    val isFavourite: Boolean,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "movie_detail_id")
    val reviews: Set<Review>,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "movie_actor",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")]
    )
    val actors: MutableSet<Actor>
) {

    /**
     * This is the fix for the recursion that happens between all ManyToMany relationships for example between MovieDetail and Actors
     *
     * Remember - You can remove the following methods in either Movie or AppUser for the same result
     * Here the appUsers field was removed (but you could have also removed movies from the AppUser Data Class
     * It doesn't matter from which side you do it (AppUser or Movie)
     * */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieDetail

        if (id != other.id) return false
        if (mDbId != other.mDbId) return false
        if (title != other.title) return false
        if (overview != other.overview) return false
        if (posterPath != other.posterPath) return false
        if (backdropPath != other.backdropPath) return false
        if (directors != other.directors) return false
        if (popularity != other.popularity) return false
        if (releaseDate != other.releaseDate) return false
        if (revenue != other.revenue) return false
        if (runtime != other.runtime) return false
        if (tagline != other.tagline) return false
        if (voteAverage != other.voteAverage) return false
        if (revenue != other.revenue) return false
        if (voteCount != other.voteCount) return false
        if (isFavourite != other.isFavourite) return false
        if (reviews != other.reviews) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + mDbId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + backdropPath.hashCode()
        result = 31 * result + directors.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + revenue.hashCode()
        result = 31 * result + runtime.hashCode()
        result = 31 * result + tagline.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + revenue.hashCode()
        result = 31 * result + voteCount.hashCode()
        result = 31 * result + isFavourite.hashCode()
        result = 31 * result + reviews.hashCode()
        return result
    }
}


//  Handle lists later
//    val genres: List<MDBGenre> = listOf()
//    val trailers: List<Trailer> =
//    val actors: List<Actor> =