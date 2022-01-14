# Taxi-Service
![taxi logo](images/taxi_logo.jpg)
## General info
This project implements taxi service, which can be used 
to manage lists of drivers, cars and manufacturers. 
Project based on 3-tier architecture (Presentation layer,
Service layer, Data access layer)

## Application functionality
```
- Register and delete driver account in the system
- Add and delete car manufacturer
- Add and delete a car
- Link/unlink the car with the driver
```

## Technologies
*The following stack of technologies was used in the project:*

- Tomcat
- Servlet
- JSP
- JSTL
- JDBC
- MySQL
- Maven
- HTML, CSS

## Setup
*Step-by-step project launch information:*
```
- Install IntelliJ IDEA Ultimate and Tomcat 9.0.50
- Install MySQL and MySQL Workbench
- Clone this project
- Configure Tomcat Server
- In MySQL Workbench run the script from src/main/resources/init_db.sql
- Insert your DB properties in the ConnectionUtil class
- Run project
```
