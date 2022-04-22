#!/bin/bash

AuthKey=`uuidgen`
AuthKey=${AuthKey//-/}

# shell의 절대 경로 얻기
SHELL_PATH=`pwd -P`

echo "create docker network bridge"
sudo docker network create dockerby_network

echo "Installing Database ...."
sudo docker run -d \
--name dockerby_db \
--network dockerby_network \
-p 8483:3306 \
-e MYSQL_ROOT_PASSWORD=$AuthKey \
-e MYSQL_DATABASE=dockerby \
-v "$SHELL_PATH/DB":/docker-entrypoint-initdb.d/ \
mysql:8.0.28

DATAURL="jdbc:mysql://dockerby_db:8483/dockerby?useSSL=false&serverTimezone=UTC&autoReconnect=true"

echo "Build dockerby solution..."
# Dockerby build image
sudo docker build -t dockerby:latest .

echo "Installing dockerby solution..."
# Dockerby container run by dockerby image
sudo docker run -d --name dockerby \
-p 8482:8080 \
--network dockerby_network \
-e SPRING_DATASOURCE_URL=$DATAURL \
-e SPRING_DATASOURCE_USERNAME=root \
-e SPRING_DATASOURCE_PASSWORD=$AuthKey \
dockerby:latest

echo "Setting Auth key"
sudo docker exec -i dockerby sh -c "echo $AuthKey > /home/AuthKey"

echo Authkey = $AuthKey