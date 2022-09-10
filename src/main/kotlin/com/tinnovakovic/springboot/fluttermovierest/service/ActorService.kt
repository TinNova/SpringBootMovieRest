package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActorDetail

interface ActorService {

    fun createActor(createActor: CreateActor): RestActor
    fun getRestActorDetail(actorId: Int): RestActorDetail
    fun getRestActors(actorIds: List<Int>): List<RestActor>
    fun bulkSaveActors(createActors: List<CreateActor>)
}