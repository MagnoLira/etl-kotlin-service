# etl-kotlin-service
A backend service for performing basic ETL (Extract, Transform, Load) operations. It allows ingesting data from CSV files, applying simple transformations (such as dropping nulls or renaming columns), and loading the processed data into a PostgreSQL database. Built with Kotlin and designed to be easily extensible.


## Features

- Modular transformation logic
- Clean project architecture (Controller, Service, Model)
- Load data into PostgreSQL
- Job metadata logging
- Configurable through `application.properties`





# Structure
├── data/ # Input CSV files
├── gradle/ # Gradle wrapper files
├── src/
│ └── main/
│ ├── kotlin/
│ │ └── com/magno/etlservice/
│ │ ├── controller/ # HTTP layer
│ │ ├── model/ # Data classes (e.g., EtlJobRequest.kt)
│ │ ├── scheduler/ # Scheduled jobs (if implemented)
│ │ ├── service/ # Business logic for ETL
│ │ └── EtlserviceApplication.kt # Entry point
│ └── resources/
│ ├── static/ # Static resources (optional)
│ ├── templates/ # HTML templates (optional)
│ └── application.properties # Environment configs
├── test/ # Test cases
├── build.gradle.kts # Build configuration
├── settings.gradle.kts # Gradle settings
├── .gitignore
├── README.md











# SAMPLE PAYLOAD:

{
  "source": {
    "type": "csv",
    "path": "./data/input.csv"
  },
  "transformations": [
    { "type": "drop_nulls" },
    { "type": "rename_column", "from": "nome_antigo", "to": "cliente_nome" }
  ],
  "destination": {
    "type": "postgresql",
    "table": "clientes"
  }
}

