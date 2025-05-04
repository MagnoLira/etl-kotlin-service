package com.magno.etlservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EtlserviceApplication

fun main(args: Array<String>) {
	runApplication<EtlserviceApplication>(*args)
}
