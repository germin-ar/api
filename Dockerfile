FROM eclipse-temurin:17-jdk-alpine as builder

COPY . /app
WORKDIR /app
RUN ./gradlew clean build -x test --info --stacktrace --no-daemon

FROM eclipse-temurin:17-jre-alpine

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
