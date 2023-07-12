FROM eclipse-temurin:20.0.1_9-jre
EXPOSE 8080
EXPOSE 8081
RUN mkdir /app
COPY ./build/install/backend  /app
WORKDIR /app/bin
CMD ["./backend"]