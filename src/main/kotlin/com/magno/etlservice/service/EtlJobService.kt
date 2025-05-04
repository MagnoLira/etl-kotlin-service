package com.magno.etlservice.service

import com.magno.etlservice.model.EtlJobRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import com.opencsv.CSVReaderHeaderAware
import java.io.FileReader
import java.sql.DriverManager

@Service
class EtlJobService {  // Certifique-se de que o nome da classe seja exatamente esse
    private val logger = LoggerFactory.getLogger(EtlJobService::class.java)

    fun runJob(request: EtlJobRequest) {
        logger.info("Executando ETL JOB")

        if (request.source.type != "csv") {
            throw IllegalArgumentException("Only csv files are supported")
        }

        val sourcePath = request.source.from 
        val tableName = request.destination.table 

        logger.info("Reading csv of: $sourcePath")

        val conn = DriverManager.getConnection("YOUR-URL")

        val logSql = """
            CREATE TABLE IF NOT EXISTS public.etl_job_log (
                id SERIAL PRIMARY KEY,
                source TEXT,
                destination TEXT,
                transformations TEXT,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """.trimIndent()
        conn.createStatement().execute(logSql)

        val insertLogSql = """
            INSERT INTO etl_job_log (source, destination, transformations)
            VALUES (?, ?, ?);
        """.trimIndent()
        val logStmt = conn.prepareStatement(insertLogSql)
        logStmt.setString(1, sourcePath)
        logStmt.setString(2, tableName)
        logStmt.setString(3, request.transformations.joinToString(";") { "${it.type}:${it.from}->${it.to}" })
        logStmt.executeUpdate()

        val reader = CSVReaderHeaderAware(FileReader(sourcePath))
        val records = mutableListOf<Map<String, String>>()
        val headers = mutableSetOf<String>()

        var row = reader.readMap()
        while (row != null) {
            val transformedRow = mutableMapOf<String, String>()

            for ((original, value) in row) {
                // Se o valor for nulo ou vazio, substitua por um valor padrão
                val processedValue = value?.takeIf { it.isNotBlank() } ?: "N/A" // ou outro valor padrão de sua escolha

                // Aplicar transformação, se houver
                val transformation = request.transformations.find { it.from == original }

                // Se houver transformação, usa o 'to' da transformação, senão, usa o próprio nome original
                val newName = transformation?.to ?: original
                transformedRow[newName] = processedValue
                headers.add(newName)
            }

            records.add(transformedRow)
            row = reader.readMap()
        }

        val createTableSql = buildString {
            append("CREATE TABLE IF NOT EXISTS $tableName (")
            append(headers.joinToString(", ") { "$it TEXT" })
            append(");")
        }
        conn.createStatement().execute(createTableSql)

        val insertSql = "INSERT INTO $tableName (${headers.joinToString(", ")}) VALUES (${headers.joinToString(", ") { "?" }})"
        val stmt = conn.prepareStatement(insertSql)

        for (record in records) {
            headers.forEachIndexed { i, col ->
                stmt.setString(i + 1, record[col]?:"N/A")
            }
            stmt.addBatch()
        }

        stmt.executeBatch()
        conn.close()

        logger.info("ETL finalizado com ${records.size} registros inseridos.")
    }
}

