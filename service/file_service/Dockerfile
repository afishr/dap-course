ARG VERSION=8u151

FROM openjdk:${VERSION}-jdk
EXPOSE 3050:3050

COPY . /file_service
WORKDIR /file_service
RUN ./gradlew installDist

RUN mkdir /app
COPY ./build/install/file_service/ /app/
WORKDIR /app/bin
CMD ["./file_service"]
