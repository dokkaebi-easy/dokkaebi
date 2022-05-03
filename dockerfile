FROM openjdk:11-jdk as builder
COPY ./BE .
RUN chmod +x ./gradlew
RUN ./gradlew clean build

FROM node:16.14.0-alpine as react
COPY --from=builder /build/libs/*.jar /app.jar
COPY ./FE .

RUN npm install
RUN npm run build

FROM openjdk:11-jdk
COPY --from=react /build /usr/share/nginx
COPY --from=react /app.jar /home/conf/app.jar

# FROM openjdk:11-jdk
# COPY /FE/build /usr/share/nginx
# COPY /BE/build/libs/*.jar /home/conf/app.jar

RUN apt update &&  apt install -y mariadb-server-10.5 \
    && apt install -y uuid-runtime && apt update \
     && apt install -y git && apt update \
     && apt install -y nginx

COPY ./install.sh /home/conf/install.sh
COPY ./DB /home/conf/db

# install NGINX
COPY ./default.conf /etc/nginx/sites-enabled/default

ENTRYPOINT [ "bin/bash", "home/conf/install.sh" ]