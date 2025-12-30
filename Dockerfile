FROM openjdk:22-jdk
ADD target/Ecommerce-backend.jar Ecommerce-backend.jar
ENTRYPOINT ["java","-jar","Ecommerce-backend.jar"]