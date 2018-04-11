FROM openjdk:8
ADD target/AirlineReservationSystem275.jar AirlineReservationSystem275.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "AirlineReservationSystem275.jar"]