package com.magno.etlservice.model

// Logical structure of the entities

data class EtlJobRequest (
    val source: Source,
    val transformations: List<Transformations>, // It can receive multiple transformations
    val destination: Destination
)

data class Source(
    val type: String,
    val from: String? = null, 
    val toField: String? = null
)

data class Transformations(
    val type: String,
    val from: String? = null, 
    val to: String? = null
)


data class Destination(
    val type: String, 
    val table: String
)