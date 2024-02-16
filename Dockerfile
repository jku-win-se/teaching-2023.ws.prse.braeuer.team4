FROM openjdk:17-alpine
COPY target/Fahrtenbuch-1.0.0.war Fahrtenbuch-1.0.0.war
ENTRYPOINT ["java","-jar","/Fahrtenbuch-1.0.0.war"]