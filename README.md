# URL-Shortener
Sample url-shortener rest api service.
# Technology Stack

- [Java](https://www.java.com) ( version 1.11 )
- [Spring Boot](https://spring.io/projects/spring-boot) ( version 2.4.2 )
- [h2 database](https://www.h2database.com) (version 1.4.2)    

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

## Deployment and RUN
To run without deploying on Docker:
navigate to /target directory and run:
```shell script 
java -jar url-shortener.jar
```

To run by deploying on Docker run:
```shell script
docker build -f Dockerfile -t url-shortener:0.1 .
docker container run -p 9001:9001 -d url-shortener:0.1
```
Then 

In order to view the endpoints visit: 
http://localhost:9001/swagger-ui/



## Database
The h2 database is used and its db file is located at ./data/db<br/>
H2 console is available in http://localhost:9001/h2-console

- Username: sa
- Password: pass






