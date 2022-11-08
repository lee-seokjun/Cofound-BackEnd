FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/cofound-config-1.0.jar cofound-config-1.0.jar
COPY apiEncryptionKey.jks apiEncryptionKey.jks
ENTRYPOINT ["java","-jar","cofound-config-1.0.jar"]