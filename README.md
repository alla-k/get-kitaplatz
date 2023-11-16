# get-kitaplatz
Pet project: Java, Spring, Kafka
## Read Me First
This is WIP. Just to practice some concepts I want to refresh/study, using microservices, Spring and Kafka. 

The idea came from the fact, that when applying for Kita place in my area, there is no common shared system, and parents have no idea what their place is the queue for the particular kita is and what their chances are. And no idea how the places are actually distributed by kitas.

It is a system to manage Kita applications. It allows user to send application for Kita place, 
to see information about possible place in queue. For Kita it provides functionality to manage available places.
It consists of 4 microservices:
- place-service: This is the main service that handles kita applications queues and place distribution.
- account-service: This service handles the user accounts and applications.
- kita-service: This service handles the Kita data and place management.
- auth: This service handles authentification. 
  
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
