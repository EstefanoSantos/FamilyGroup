FROM eclipse-temurin:21

LABEL maintener="Estefano Santos <estefanosantosbarros@gmail.com>"

COPY . .

RUN ./mvnw clean install -DskipTests

ENTRYPOINT [ "java", "-jar", "target/familygroup-0.0.1-SNAPSHOT.jar"]



