FROM amazoncorretto:17-alpine3.16-jdk
EXPOSE 8080
WORKDIR /app
COPY /build/libs/UserManagementService-0.0.1.jar UserManagementService-0.0.1.jar
CMD ["java", "-jar", "UserManagementService-0.0.1.jar"]