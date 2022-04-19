FROM node:16.14.0-alpine as react
COPY ./FE .
RUN npm install
RUN npm run build

FROM openjdk:11 as builder
COPY ./BE .
COPY --from=react /build /src/main/resources/static/

RUN ls -l
RUN chmod +x ./gradlew
RUN ./gradlew clean build

FROM openjdk:11
COPY --from=builder /build/libs/*.jar app.jar

ENTRYPOINT [ "java", "-jar","./app.jar" ]