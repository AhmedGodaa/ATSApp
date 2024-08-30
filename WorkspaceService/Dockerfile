FROM amazoncorretto:17-alpine3.16-jdk
EXPOSE 8086
WORKDIR /app
COPY /build/libs/WorkspaceService-0.0.1.jar WorkspaceService-0.0.1.jar
CMD ["java", "-jar", "WorkspaceService-0.0.1.jar"]