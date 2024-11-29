FROM eclipse-temurin:21
ARG MONGODB_USER
ARG MONGODB_PASSWORD
ENV MONGODB_USER_ENV=$MONGODB_USER
ENV MONGODB_PASSWORD_ENV=${MONGODB_PASSWORD}
#RUN echo "1: " + ${MONGODB_USER_ENV}
#RUN echo "2: " + $MONGODB_USER_ENV
WORKDIR /app
COPY build/libs/joblisting-0.0.1.jar joblisting-0.0.1.jar
EXPOSE 8080
#ENTRYPOINT ["java","-jar","-Ddb.mongo.user=$MONGODB_USER -Ddb.mongo.password=$MONGODB_PASSWORD","joblisting-0.0.1.jar"]
CMD java -jar -Ddb.mongo.user=$(echo ${MONGODB_USER_ENV} -Ddb.mongo.password=$(echo ${MONGODB_PASSWORD_ENV} joblisting-0.0.1.jar
