FROM 10.0.2.5:5000/java8:latest

MAINTAINER linjensor@gmail.com on 2018/01/26

COPY app.jar /opt
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/opt/app.jar"]