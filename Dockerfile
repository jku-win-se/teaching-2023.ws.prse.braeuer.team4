FROM openjdk:17-alpine
COPY target/Fahrtenbuch-0.0.1-SNAPSHOT.war Fahrtenbuch-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/Fahrtenbuch-0.0.1-SNAPSHOT.war"]