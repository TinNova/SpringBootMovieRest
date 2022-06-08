package com.tinnovakovic.springboot.fluttermovierest.service

interface BulkDownloadService {

    /**
     * bulkDownLoadData downloads Movies & Actors (cast) data, this populates the database
     */
    fun bulkDownloadData()
}