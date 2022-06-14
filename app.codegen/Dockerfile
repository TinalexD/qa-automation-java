FROM --platform=linux/amd64 adoptopenjdk/openjdk11:centos-jre
WORKDIR /usr/share/app
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev,api-docs", "--spring.liquibase.contexts=dev,faker"]
COPY build/app.jar ./
