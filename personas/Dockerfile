# Fase de construcción con Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar archivos pom.xml y fuente desde la carpeta del proyecto
COPY pom.xml .
COPY src ./src

# Construir la aplicación (sin ejecutar tests para acelerar la construcción)
RUN mvn package -DskipTests

# Fase de ejecución con OpenJDK
FROM openjdk:17-slim
WORKDIR /app

# Copiar el JAR desde la fase de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (debe coincidir con el puerto configurado en application.yml)
EXPOSE 8082

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
