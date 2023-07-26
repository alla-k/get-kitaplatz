# get-kitaplatz
Pet project: Java, Spring, Kafka
## Read Me First
This is WIP. 
This is a simple pet project to test using the microservices setup with Kafka messaging.
It is a system to manage Kita applications. It allows user to send application for Kita place, 
to see information about possible place in queue. For Kita it provides functionality to manage available places.
It consists of 3 microservices:
- place-service: This is the main service that handles kita applications queues and place distribution.
- account-service: This service handles the user accounts and applications.
- kita-service: This service handles the Kita data and place management.

## Getting Started
### Prerequisites
If using Docker: build the multiple database image with the following command:
```shell
docker build -t multi-postgres .
```
Run the docker-compose file to start the databases and Kafka:
```shell
docker-compose up
```
