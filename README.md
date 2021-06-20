# URL-Shortener
Sample url-shortener rest api service.
# Technology Stack

- [Java](https://www.java.com) ( version 1.11 )
- [Spring Boot](https://spring.io/projects/spring-boot) ( version 2.4.2 )
- [h2 database](https://www.h2database.com) (version 1.4.2)    
- [Swagger 3](https://swagger.io/specification/) (version 3.0.0)

## Requirements 
Apache Maven (version: 3.6.3)<br/>
Download Apache Maven and Java 11.

## Build
To build the project, run

```shell script
git clone https://github.com/mortezanaeimabadi/url-shortener.git
cd url-shortener
mvn clean package
```

## Deploy locally and RUN
To run without deploying on Docker:

navigate to /target directory and run:
```shell script 
java -jar url-shortener.jar
```
### Database
If run without deploying on docker, 
you can access the H2 database:
 http://localhost:9001/h2-console

- Username: sa
- Password: pass

## Deploy on Docker and RUN
To run by deploying on Docker, 
navigate to project root (url-shortener) directory and run:
```shell script
docker build -f Dockerfile -t url-shortener:0.1 .
docker container run -p 9001:9001 -d url-shortener:0.1
```

## Test endpoints (SWAGGER)
Then 
In order to view the endpoints visit: 
http://localhost:9001/swagger-ui/











