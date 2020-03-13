#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY ./src ./workspace/app/src
COPY ./pom.xml ./workspace/app/pom.xml
RUN mvn -f ./workspace/app/pom.xml clean compile package -DskipTests
RUN mkdir -p ./workspace/app/target/dependency && (cd ./workspace/app/target/dependency; jar -xf ../*.jar)
 
FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.loganconnor44.ToDoRestAPIApplication"]