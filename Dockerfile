FROM eclipse-temurin:17-jre as build

MAINTAINER edeesan

COPY build/libs/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]

