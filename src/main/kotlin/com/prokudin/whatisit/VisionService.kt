package com.prokudin.whatisit

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class VisionService {
    private val log: Logger = LoggerFactory.getLogger(VisionService::class.java)

    fun checkImage() {
        log.info("Hello from VisionService!")
    }
}
