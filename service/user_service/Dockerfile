FROM openjdk:11-jdk
EXPOSE 8084:8084
RUN mkdir /app
COPY ./build/install/user_service/ /app/
WORKDIR /app/bin
CMD ["./user_service"]
