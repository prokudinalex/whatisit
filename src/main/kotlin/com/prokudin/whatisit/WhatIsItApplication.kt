package com.prokudin.whatisit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhatIsItApplication

fun main(args: Array<String>) {
    val context = runApplication<WhatIsItApplication>(*args)
    val service : VisionService = context.getBean(VisionService::class.java)
    service.checkImage()
}
