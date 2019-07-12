FROM java:8
WORKDIR /
ADD target/api-0.0.1-SNAPSHOT.jar api.jar
EXPOSE 8080
CMD java -jar api.jar --spring.profiles.active=dev
