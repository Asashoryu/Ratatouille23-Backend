# Utilizza un'immagine di base con Java e Docker installati
FROM openjdk:19

# Copia il file JAR dell'applicazione nel contenitore
COPY demo/target/Rat23.jar Rat23.jar

# Copia il file JSON dell'account di servizio Firebase nel contenitore
COPY demo/ratatouille23-grp21-firebase-adminsdk-134t0-7755617655.json /demo/ratatouille23-grp21-firebase-adminsdk-134t0-7755617655.json

# Definisci il comando per eseguire l'applicazione Spring Boot
ENTRYPOINT ["java", "-jar", "Rat23.jar"]
