FROM openjdk:18.0.1-jdk

EXPOSE 8082


WORKDIR application
RUN microdnf install git
RUN git clone https://github.com/Armando1514/spring-boot-microservices.git
RUN ( cd ./spring-boot-microservices/mssc-brewery-bom ; ./mvnw clean install)
RUN ( cd ./spring-boot-microservices/${directoryName} ; ./mvnw clean package)
WORKDIR spring-boot-microservices/${directoryName}
ENTRYPOINT ./mvnw spring-boot:run