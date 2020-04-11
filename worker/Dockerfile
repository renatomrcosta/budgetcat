FROM openjdk:13-jdk-alpine as builder

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Layers the dependencies in a few steps first
COPY build.gradle.kts settings.gradle.kts gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0

#Then adds the code proper in a second layer
COPY . .
RUN ./gradlew build


FROM openjdk:13-jdk-alpine
ENV APP_HOME=/usr/app/
ENV ARTIFACT_NAME="worker-0.0.1-SNAPSHOT.jar"
WORKDIR $APP_HOME
EXPOSE 8080
COPY --from=builder $APP_HOME/build/libs/$ARTIFACT_NAME app.jar
CMD ["java","-jar", "app.jar"]
