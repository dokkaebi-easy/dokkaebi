FROM node:16.14.0-alpine as react
COPY ./FE .
RUN npm install
RUN npm run build

FROM openjdk:11-jdk as builder
COPY ./BE .
COPY --from=react /build /src/main/resources/static/

RUN chmod +x ./gradlew
RUN ./gradlew clean build

FROM openjdk:11-jdk
COPY --from=builder /build/libs/*.jar app.jar

COPY ./keygen.sh keygen.sh
COPY ./install.sh install.sh
COPY ./DB ./DB
RUN bin/bash keygen.sh \
    && apt update &&  apt install -y mariadb-server-10.5

ENTRYPOINT [ "bin/bash", "./install.sh" ]