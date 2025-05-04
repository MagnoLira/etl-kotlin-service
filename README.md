# etl-kotlin-service
A backend service for performing basic ETL (Extract, Transform, Load) operations. It allows ingesting data from CSV files, applying simple transformations (such as dropping nulls or renaming columns), and loading the processed data into a PostgreSQL database. Built with Kotlin and designed to be easily extensible.





## Current - Features

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


Cloud Database: [console.neon.tech](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwjsiqCHgYuNAxWtr5UCHWAABtAQFnoECAkQAQ&url=https%3A%2F%2Fneon.tech%2F&usg=AOvVaw2V-qc7rmEG-pLkc_6mJupK&cshid=1746402786191605&opi=89978449)
### New features - Perspective 

- Support for multiple data sources (e.g., MySQL, APIs, JSON, Excel)


- DSL for transformation and validation rules


- Job orchestration with Airflow or Quartz


- Authentication and authorization (JWT-based)


- Web interface for job submission and monitoring


- Monitoring dashboard with Prometheus / Grafana


- Data quality integration with Great Expectations


- Retry and error-handling mechanisms


- Job history and status tracking


- Logging and alerting system


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
