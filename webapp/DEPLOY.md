# Deploying Student Management System (webapp)

This document describes simple steps to build and deploy the Spring Boot webapp.

1) Build the JAR locally (recommended)

   - Ensure you have a JDK and Maven installed.
   - From `webapp/` run:

       mvn -DskipTests package
       java -jar target/student-management-system-1.0.0.jar

2) Environment variables

The application reads critical configuration from environment variables (with safe defaults in development):

- SPRING_DATASOURCE_URL (default: jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true)
- SPRING_DATASOURCE_USERNAME (default: root)
- SPRING_DATASOURCE_PASSWORD (default: local password)
- SPRING_JPA_HIBERNATE_DDL_AUTO (default: update)
- APP_JWT_SECRET (default placeholder)

Set these in your CI/CD pipeline or container runtime. Example (PowerShell):

    $env:SPRING_DATASOURCE_URL='jdbc:mysql://db:3306/studentdb?useSSL=false&serverTimezone=UTC'
    $env:SPRING_DATASOURCE_USERNAME='smsuser'
    $env:SPRING_DATASOURCE_PASSWORD='securepassword'
    java -jar target/student-management-system-1.0.0.jar

3) Docker build and run

From `webapp/`:

    docker build -t sms-webapp:latest .
    docker run -e SPRING_DATASOURCE_URL='jdbc:mysql://host.docker.internal:3306/studentdb' -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=secret -p 8080:8080 sms-webapp:latest

4) Security

This repo uses a simple username/password signup/login flow. For production you should:

- Use HTTPS
- Use a strong `APP_JWT_SECRET` and implement token-based auth or session management
- Do not keep passwords or secrets in `application.properties` in the repository

5) Database migrations

This project currently uses `spring.jpa.hibernate.ddl-auto` settable via environment variable. For production, prefer a proper migration tool such as Flyway or Liquibase.
