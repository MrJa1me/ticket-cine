FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="TicketCine"
WORKDIR /app
COPY target/cl-ticketcine-compras-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "app.jar"]
