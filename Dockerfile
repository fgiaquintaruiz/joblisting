FROM eclipse-temurin:21
ENV MONGODB_HOST=${MONGODB_HOST} MONGODB_USER=${MONGODB_USER} MONGODB_PASSWORD=${MONGODB_PASSWORD} MONGODB_LOCAL_PORT=${MONGODB_LOCAL_PORT}
WORKDIR /app
COPY build/libs/joblisting-0.0.1.jar joblisting-0.0.1.jar
EXPOSE 8080
CMD ["java -Ddb.mongo.host=$MONGODB_HOST -Ddb.mongo.user=$MONGODB_USER -Ddb.mongo.password=$MONGODB_PASSWORD -Ddb.mongo.port=$MONGODB_LOCAL_PORT","-jar","joblisting-0.0.1.jar"]
