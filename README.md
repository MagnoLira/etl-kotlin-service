# etl-kotlin-service
A backend service for performing basic ETL (Extract, Transform, Load) operations. It allows ingesting data from CSV files, applying simple transformations (such as dropping nulls or renaming columns), and loading the processed data into a PostgreSQL database. Built with Kotlin and designed to be easily extensible.


## Features

- Modular transformation logic
- Clean project architecture (Controller, Service, Model)
- Load data into PostgreSQL
- Job metadata logging
- Configurable through `application.properties`



The project follows a structured and scalable architecture:

    etl-kotlin-service/
    ├── data/                             # Input CSV files for ETL
    ├── gradle/                           # Gradle wrapper files
    ├── src/
    │   └── main/
    │       ├── kotlin/
    │       │   └── com/magno/etlservice/
    │       │       ├── controller/       # REST controllers to expose ETL endpoints
    │       │       ├── model/            # Data classes (e.g., EtlJobRequest.kt)
    │       │       ├── scheduler/        # Scheduled ETL jobs (optional)
    │       │       ├── service/          # Core ETL business logic
    │       │       └── EtlserviceApplication.kt  # Spring Boot application entry point
    │       └── resources/
    │           ├── static/               # Static resources (e.g., frontend assets, optional)
    │           ├── templates/            # HTML templates (optional, for Thymeleaf, etc.)
    │           └── application.properties  # Environment configuration (DB credentials, etc.)
    ├── test/                             # Unit and integration test cases
    ├── build.gradle.kts                 # Build configuration for the project
    ├── settings.gradle.kts              # Gradle project settings
    ├── .gitignore                        # Files and directories ignored by Git
    ├── README.md                         # Project documentation
### Important Links

Spring initalizr: https://start.spring.io 


### Example Payload

```json
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
