FROM eclipse-temurin:21
WORKDIR /app
COPY build/libs/joblisting-0.0.1.jar joblisting-0.0.1.jar
EXPOSE 8080
CMD ["java","-jar","joblisting-0.0.1.jar"]