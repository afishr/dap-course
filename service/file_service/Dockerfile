FROM openjdk:8-jdk
EXPOSE 3050:3050
RUN mkdir /app
COPY ./build/install/file_service/ /app/
WORKDIR /app/bin
CMD ["./file_service"]
