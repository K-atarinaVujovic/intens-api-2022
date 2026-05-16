# syntax=docker/dockerfile:1

FROM eclipse-temurin:8-jdk-jammy AS build-stage

######### DEPENDENCIES #########
WORKDIR /build

# Copy the mvnw wrapper with executable permissions.
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

# Cache dependencies
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

######### PACKAGES #########

COPY ./src src/
# Compile into JAR and rename it
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar


######### EXTRACTING JAR LAYERS #########

# Unpack the jar for efficiency later
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

######### FINAL #########
FROM eclipse-temurin:8-jre-jammy AS final

# Create a non-privileged user that the app will run under.
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable from the "package" stage.
COPY --from=build-stage build/target/extracted/dependencies/ ./
COPY --from=build-stage build/target/extracted/spring-boot-loader/ ./
COPY --from=build-stage build/target/extracted/snapshot-dependencies/ ./
COPY --from=build-stage build/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT [ "java", "org.springframework.boot.loader.JarLauncher" ]
