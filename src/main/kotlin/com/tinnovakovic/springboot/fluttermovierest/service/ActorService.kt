package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActorDetail

interface ActorService {

    fun createActor(createActor: CreateActor): RestActor
    fun getActorDetail(actorId: Int): RestActorDetail
    fun getActors(actorIds: List<Int>): List<RestActor>
}