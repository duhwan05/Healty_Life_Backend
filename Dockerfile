# =============================== Start of Build Stage =============================== #
FROM gradle:7.5.1-jdk17 AS build

# 소스 코드 복사
COPY . /app

WORKDIR /app

# Gradle 빌드 수행
# chmod +x ./gradlew
RUN chmod +x ./gradlew
RUN ./gradlew clean build --no-daemon --info > build.log 2>&1 || (cat build.log && exit 1)

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# 애플리케이션 실행
CMD ["java", "-jar", "build/libs/healthyLife-0.0.1-SNAPSHOT.jar"]

