#!/bin/bash

AuthKey=`uuidgen`
AuthKey=${AuthKey//-/}

# shell의 절대 경로 얻기
SHELL_PATH=`pwd -P`

echo "create docker network bridge"
sudo docker network create dokkaebi_network

echo "Installing Database ...."
sudo docker run -d \
--name dokkaebi_db \
--network dokkaebi_network \
-p 8483:3306 \
-e MYSQL_ROOT_PASSWORD=$AuthKey \
-e MYSQL_DATABASE=dokkaebi \
-v "$SHELL_PATH/DB":/docker-entrypoint-initdb.d/ \
mysql:8.0.28

DATAURL="jdbc:mysql://dokkaebi_db:8483/dokkaebi?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&autoReconnect=true"

echo "Build dokkaebi solution..."
# dokkaebi build image
sudo docker build -t dokkaebi:latest .

echo "Installing dokkaebi solution..."
# dokkaebi container run by dokkaebi image
sudo docker run -d --name dokkaebi \
-p 8482:8080 \
--network dokkaebi_network \
-e SPRING_DATASOURCE_URL=$DATAURL \
-e SPRING_DATASOURCE_USERNAME=root \
-e SPRING_DATASOURCE_PASSWORD=$AuthKey \
dokkaebi:latest

echo "Setting Auth key"
sudo docker exec -i dokkaebi sh -c "echo $AuthKey > /home/AuthKey"

echo Authkey = $AuthKey