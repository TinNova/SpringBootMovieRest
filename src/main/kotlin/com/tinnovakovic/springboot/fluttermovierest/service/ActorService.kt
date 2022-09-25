package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.Actor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActorDetail
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail

interface ActorService {

    fun createActor(createActor: CreateActor): RestActor
    fun getRestActorDetail(actorId: Int): RestActorDetail
    fun getRestActors(actorIds: List<Int>): List<RestActor>
    fun getActors(actorIds: List<Int>): List<Actor>
    fun getFavouriteActors(userId: Int): List<RestActorDetail>

    fun bulkSaveActors(createActors: List<CreateActor>)
}