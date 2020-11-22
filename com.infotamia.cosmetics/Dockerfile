FROM openjdk:11-jre-slim-stretch
WORKDIR /

COPY ["target/cosmetics.jar", "cosmetics.jar"]
COPY ["target/classes", "classes"]
COPY ["target/dependency", "dependency"]
EXPOSE 8080
CMD sleep 5 && java -cp classes:dependency/*:/etc/backend/cosmetics-config/ com.infotamia.cosmetics.application.CosmeticApplication
