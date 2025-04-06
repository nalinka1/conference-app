# Conference Session Management API

This project provides a REST API for managing conference session submissions. The API is built using Dropwizard and integrates with a MySQL database to store session details. It includes basic authentication using API keys and is packaged as a Docker container for easy deployment.

## Features

- **Session Management**: Create, retrieve, update, and delete conference sessions.
- **Database Integration**: Uses **MySQL** for storing session data.
- **Basic Authentication**: Secured using an API key passed in the headers.
- **Logging**: Comprehensive logging of application events and errors.
- **Docker Support**: Application is packaged as a Docker container for easy deployment.

## Prerequisites

To run this application locally, you need to have the following installed:

- **Java 20** (or any compatible version)
- **Maven** for building the project
- **Docker** for containerization
- **MySQL** for database management (or Dockerized MySQL instance)

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/conference-session-api.git
cd conference-session-api
```
### Configure MYSQL

The application requires a MySQL database to store session data. You can either set up MySQL locally or use Docker.

#### MySQL Configuration
If you're using MySQL locally, create a database with the following SQL command:

```sql
CREATE DATABASE conference_db;
```

#### Create the table and add index

```sql
CREATE TABLE sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    speaker_name VARCHAR(255) NOT NULL,
    session_date DATE,
    time_slot_id INT,
    INDEX idx_speaker (speaker_name),
    INDEX idx_date (session_date),
    INDEX idx_slot (time_slot_id)
);

```
## API Endpoints

- **POST /sessions**: Create a new session.
- **GET /sessions/{id}**: Retrieve session details by ID.
- **GET /sessions**: Retrieve all sessions.
- **PUT /sessions/{id}**: Update session details by ID.
- **DELETE /sessions/{id}**: Delete a session by ID.

## Access the API
Once the application is running, you can access the API at 
```
http://localhost:8080
```

Use the API key test for basic authentication in the request headers.
