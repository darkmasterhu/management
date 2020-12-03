# management
Simple machine management spring boot application with docker

Used stack:
- Java 11
- Spring Boot 2.4
- Postgre SQL
- Flyway
- Junit5, Mockito
- Docker (docker-compose)

## How to run

- Checkout the project
- Goto folder 'docker', and run the following command:

```
docker-compose up -d
```

- Open browser and type in the following url:

```
http://localhost:8080/management
```

After this you can see a simple landing page :)

- You can use the application with collaboration of Postman (or other rest client application) 
  To achieve this, open the Postman, and import the following collection:
  
```
postman/postman_management.json
```

After this you can try out the api calls.

- You can achieve the Postgres database with the following parameters:

```
url     : jdbc:postgresql://localhost:5432/management
usr/pwd : postgres
```

- If you want to develop the project, you can build this project with command: 

```
gradlew build
```

- You can achieve the builded application on the following dockerhub repository:

```
darkmasterhu/management:latest
```

- You can achieve the application source code on GitHub:

```
https://github.com/darkmasterhu/management
```