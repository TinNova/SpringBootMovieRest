package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.mapper.MdbMapper
import com.tinnovakovic.springboot.fluttermovierest.mdb_models.MdbMovieDetail
import com.tinnovakovic.springboot.fluttermovierest.mdb_models.MdbMoviesResult
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class BulkRepoImpl(private val mdbMapper: MdbMapper) : BulkRepo {

    @Value("\${api_key}")
    private val apiKey: String? = null

    override fun bulkDownloadMovies(): List<RestMovieDetail> {
        //Create a new RestTemplate and use getForObject to make a GET request
        //to the server and return an instance of Quote representing the response
        val mdbMovieResult: MdbMoviesResult? = RestTemplate().getForObject("https://api.themoviedb.org/3/movie/popular?api_key=${apiKey}&language=en-US&page=1", MdbMoviesResult::class.java)

        val mdbMovieDetails: MutableList<MdbMovieDetail> = mutableListOf()
        mdbMovieResult!!.results.forEach { mdbMovie ->

            val mdbMovieDetail: MdbMovieDetail? = RestTemplate().getForObject("https://api.themoviedb.org/3/movie/${mdbMovie.id}?api_key=${apiKey}&language=en-US", MdbMovieDetail::class.java)
            mdbMovieDetail?.let {
                mdbMovieDetails.add(it)
            }
        }

        //Print the response to the console
        println(mdbMovieDetails)



        return mdbMapper.mapToRestMovies(mdbMovieDetails)
       // TODO("Not yet implemented")
    }

    override fun bulkDownloadActors() {
       // TODO("Not yet implemented")
    }

    override fun bulkDownloadReviews() {
       // TODO("Not yet implemented")
    }


}