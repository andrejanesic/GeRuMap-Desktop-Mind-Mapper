# https://www.jetbrains.com/help/idea/running-a-java-app-in-a-container.html

### Build ###

FROM maven:ibmjava as build

ENV HOME=/app

RUN mkdir -p $HOME

WORKDIR $HOME

ADD pom.xml $HOME

RUN mvn verify --fail-never

ADD . $HOME

RUN mvn package

### Exec ###

FROM ibmjava:jre

RUN apt-get update

RUN apt-get install libxext6 libxrender1 libxtst6 libxi6 libxft2 -y

COPY --from=build /app/target/main.jar /app/main.jar

ENTRYPOINT java -jar /app/main.jar