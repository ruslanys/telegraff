FROM openjdk:8-jdk-alpine

WORKDIR root/application

EXPOSE 8080

CMD ./gradlew installDist && ./build/install/telegraff-sample/bin/telegraff-sample