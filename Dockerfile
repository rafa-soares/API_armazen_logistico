# Imagem base com Java 21 leve
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR da aplicação para o container
COPY target/wms-receiving-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que sua aplicação usa (ajuste se necessário)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]