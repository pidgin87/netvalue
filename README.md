# Read Me First

**Hello, NetValue!**
This is application that manages charge points used to charge electric vehicles.

# Getting Started

### 1. Open project as maven project

Use JDK 11

### 2. Run project installation

Execute ```./mvnw install```

During installation, controllers and it's models will be generated from API specification at
```src/openapi/openapi.yaml```

### 3. Run application using Docker

Execute command ```./environment.sh``` <br/>
The application will be available at: ```http://localhost:8080```

**NOTE:_You have to first install and run [Docker](https://www.docker.com/) on your machine_**

### 4. Call REST-requests to application

You can use Intellij IDEA you can run samples of REST-api in source code at: ```/src/request```

### 5. Local debug

You can also run application locally, for example using Intellij IDEA
```src/main/java/nz/netvalue/ApplicationRunner.java```<br/>