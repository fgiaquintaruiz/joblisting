#!/bin/sh

#java -jar joblisting-0.0.1.jar -Ddb.mongo.user=mongodb2024 -Ddb.mongo.password=Paula0608$

#java -jar build/libs/joblisting-0.0.1.jar -config=1 -port=6868 -P:db.mongo.user=$(echo ${MONGODB_USER_ENV}) -P:db.mongo.password=$(echo ${MONGODB_PASSWORD_ENV})

java -jar joblisting-0.0.1.jar -config=1 -port=6868 -P:db.mongo.user=$(echo ${MONGODB_USER_ENV}) -P:db.mongo.password=$(echo ${MONGODB_PASSWORD_ENV})

#java -jar joblisting-0.0.1.jar --db.mongo.user=$(echo ${MONGODB_USER_ENV}) --db.mongo.password=$(echo ${MONGODB_PASSWORD_ENV})