FROM eclipse-temurin:21-jre

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]