FROM gradle:8.14-jdk21 AS build
WORKDIR /workspace
COPY . .
RUN gradle clean buildFatJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/build/libs/spotify-backend-all.jar /app/app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
