FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/fedevara/ip-tracker

FROM maven:3.6.3-jdk-11 as build
WORKDIR /app
COPY --from=clone /app/ip-tracker /app
RUN mvn install

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/IpTracker-1.0-SNAPSHOT-jar-with-dependencies.jar /app
EXPOSE 8079
CMD ["java", "-jar", "IpTracker-1.0-SNAPSHOT-jar-with-dependencies.jar"]