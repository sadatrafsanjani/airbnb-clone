Background:

This application is consists of five modules:

1) Authenticator service
2) Service Registry
3) API Gateway
4) Rental Processor
5) Rental Search

The authenticator service generates a JWT token with refresh token and expiration time which is saved on database. 
Every other micro-service (rental processor/search) is secured and requres this JWT token to accept request. 

The Booking api calls rental processor from rental search and it is concurrency enabled. All api has been implemented as required.


Technology:

1) Spring Boot
2) Eureka Client/Server
3) PostgreSQL


Database Schema:

![schema](schema.png)