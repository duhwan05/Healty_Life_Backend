# =============================== Start of Build Stage =============================== #
FROM gradle:7.5.1-jdk17 AS build

# 소스 코드 복사
COPY . /app

WORKDIR /app

# Gradle 빌드 수행
# chmod +x ./gradlew
RUN chmod +x ./gradlew
RUN ./gradlew clean build

# 애플리케이션 실행
CMD ["java", "-jar", "build/libs/healthyLife1.0.jar"]

