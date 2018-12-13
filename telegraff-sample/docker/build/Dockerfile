# Build
FROM openjdk:8-jdk-alpine as build
WORKDIR /root/application
COPY . .
RUN ./gradlew clean build installDist

# Image
FROM openjdk:8-jre-alpine
WORKDIR /root/application
COPY --from=build /root/application/build/install /root/

EXPOSE 8080
CMD /root/telegraff-sample/bin/telegraff-sample