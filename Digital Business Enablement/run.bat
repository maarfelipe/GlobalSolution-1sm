cd ./cropsage
mvn clean package -DskipTests
cd ..
docker-compose up -d --build