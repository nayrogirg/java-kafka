# java-kafka

This project demonstrates how to use Apache Kafka with a Spring Boot application. It includes a consumer that listens to JSON messages and stores them in a database.

## Prerequisites

- Docker
- Docker Compose

1. Clone the project
```bash
git clone https://github.com/nayrogirg/java-kafka.git
cd java-kafka 
```
2. Clean and Build the Project

```bash
mvn clean install
```
3. Run Spring Boot Application with Docker Compose Profile

```bash
java -jar target/java-kafka-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=docker-compose
```
or
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=docker-compose
```

4. Access the broker container
```bash
docker exec -it broker /bin/bash
```

5. Send JSON messages to the Kafka topic using the Kafka console producer
```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic topic
```