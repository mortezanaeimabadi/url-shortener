FROM adoptopenjdk/openjdk11
ADD target/url-shortener.jar url-shortener.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/url-shortener.jar"]