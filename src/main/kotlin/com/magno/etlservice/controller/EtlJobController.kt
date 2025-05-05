package com.magno.etlservice.controller

import com.magno.etlservice.service.EtlJobService
import com.magno.etlservice.model.EtlJobRequest
import org.springframework.web.bind.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import java.sql.DriverManager

@RestController
@RequestMapping("/etl-job")
class EtlJobController(private val etlJobService: EtlJobService) {

    private val logger = LoggerFactory.getLogger(EtlJobController::class.java)

    @PostMapping
    fun createEtlJob(@RequestBody request: EtlJobRequest): String {
        logger.info("Recebido novo job: $request")
        try {
            etlJobService.runJob(request)  
            return "Etl job executado com sucesso"
        } catch (e: Exception) {
            logger.error("Erro ao executar o ETL job", e)
            return "Erro ao executar o ETL job: ${e.message}"
        }
    }
}

@RestController
@RequestMapping("/etl-job")
class EtlHistoryController {

    @GetMapping("/history")
    fun getEtlHistory(): ResponseEntity<List<Map<String, Any>>> {
        val conn = DriverManager.getConnection("URL")
        val stmt = conn.createStatement()
        val rs = stmt.executeQuery("SELECT * FROM etl_job_log ORDER BY timestamp DESC")

        val history = mutableListOf<Map<String, Any>>()
        while (rs.next()) {
            val row = mutableMapOf<String, Any>()
            row["id"] = rs.getInt("id")
            row["source"] = rs.getString("source")
            row["destination"] = rs.getString("destination")
            row["transformations"] = rs.getString("transformations")
            row["timestamp"] = rs.getString("timestamp")
            history.add(row)
        }

        conn.close()
        return ResponseEntity.ok(history)
    }
}
