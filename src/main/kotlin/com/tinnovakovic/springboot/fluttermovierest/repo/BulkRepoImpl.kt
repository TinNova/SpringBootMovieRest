package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.mapper.MdbMapper
import com.tinnovakovic.springboot.fluttermovierest.mdb_models.*
import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.apache.commons.logging.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class BulkRepoImpl(private val mdbMapper: MdbMapper) : BulkRepo {

    @Value("\${api_key}")
    private val apiKey: String? = null

    override fun bulkDownloadMovies(): List<RestMovieDetail> {
        val mdbMovieResult: MdbMoviesResult? = RestTemplate().getForObject(
            "https://api.themoviedb.org/3/movie/popular?api_key=${apiKey}&language=en-US&page=1",
            MdbMoviesResult::class.java
        )
        println("TIN Apikey: ${apiKey}")

        val mdbMovieDetails: MutableList<MdbMovieDetail> = mutableListOf()

        mdbMovieResult!!.results.forEach { mdbMovie ->

            val mdbMovieDetail: MdbMovieDetail? = RestTemplate().getForObject(
                "https://api.themoviedb.org/3/movie/${mdbMovie.id}?api_key=${apiKey}&language=en-US",
                MdbMovieDetail::class.java
            )

            println("TIN mdbMovie Id: ${mdbMovie.id}")
            println("TIN mdbMovieDetail Budget: ${mdbMovieDetail?.budget}")
            println("TIN mdbMovieDetail Revenue: ${mdbMovieDetail?.revenue}")
            println("TIN mdbMovieDetail Revenue: ${mdbMovieDetail?.runtime}")

            mdbMovieDetail?.let {
                mdbMovieDetails.add(it)
            }
        }

        return mdbMapper.mapToRestMovieDetails(mdbMovieDetails)
    }

    override fun bulkDownloadActors(restMovieDetails: List<RestMovieDetail>): List<CreateActor> {

        val mdbActorDetails: MutableList<MdbActorDetail> = mutableListOf()
        val mdbCast: MutableList<MdbCast> = mutableListOf()

        restMovieDetails.forEach { restMovieDetail ->

            val mdbCredit: MdbCredit? = RestTemplate().getForObject(
                "https://api.themoviedb.org/3/movie/${restMovieDetail.mDbId}/credits?api_key=${apiKey}&language=en-US",
                MdbCredit::class.java
            )

            mdbCredit?.let {
                mdbCast.addAll(it.cast)
            }
        }

        mdbCast.forEach { mdbCast ->
            val mdbActorDetail: MdbActorDetail? = RestTemplate().getForObject(
                "https://api.themoviedb.org/3/person/${mdbCast.id}?api_key=${apiKey}",
                MdbActorDetail::class.java
            )

            mdbActorDetail?.let {
                mdbActorDetails.add(it)
            }
        }

        return mdbMapper.mapToCreateActors(mdbActorDetails)
    }

    override fun bulkDownloadReviews() {
        // TODO("Not yet implemented")
    }


}