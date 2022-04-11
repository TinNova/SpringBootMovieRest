package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.service.BulkDownloadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/bulk")
class BulkDownloadController(private val service: BulkDownloadService) {

    @GetMapping("/")
    fun bulkDownloadData() {
        service.bulkDownloadMovies()
    }
}