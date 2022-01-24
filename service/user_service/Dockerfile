ARG VERSION=8u151

FROM openjdk:${VERSION}-jdk
EXPOSE 8084:8084

COPY . /user_service
WORKDIR /user_service
RUN ./gradlew --no-daemon installDist

RUN mkdir /app
COPY ./build/install/user_service/ /app/
WORKDIR /app/bin
CMD ["./user_service"]
