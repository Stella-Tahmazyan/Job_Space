FROM java:8
WORKDIR /
ADD web/target/web-0.0.1-SNAPSHOT.jar web.jar
EXPOSE 8086
CMD java -jar web.jar --spring.profiles.active=production

