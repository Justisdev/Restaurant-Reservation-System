
# Restaurant Reservation System

A simple REST api to manage creation, retrieval, update and cancellation of customer reservation.




## Features

- Create new reservation
- View all customer's reservation
- Update customer reservation
- Cancel customer reservation
- Notify customer reservation
- Remind customer reservation



## Documentation

[Restaurant Reservation API Document](https://github.com/Justisdev/Restaurant-Reservation-System/blob/main/Restaurant%20Reservation%20API%20Docs.pdf)

[Restaurant Reservation System Design Document](https://github.com/Justisdev/Restaurant-Reservation-System/blob/main/Restaurant%20Reservation%20SDD.pdf)
## Tech Stack

- Java 17
- Springboot
- Mysql
## Run Locally

Install Mysql via docker

```bash
  docker run --name test-mysql -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -d mysql
```
Create database

```bash
  CREATE DATABASE reservation_db;
```
Clone the project

```bash
  git clone https://github.com/Justisdev/Restaurant-Reservation-System.git
```

Go to the project directory

```bash
  cd Restaurant Reservation Sytem
```

Run the application

```bash
  spring-boot:run
```
