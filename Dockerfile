# =============================== Start of Build Stage =============================== #
FROM gradle:7.5.1-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 캐시를 활용하기 위해 Gradle 설정 파일만 먼저 복사
COPY build.gradle settings.gradle /app/

# 종속성을 먼저 다운로드
RUN gradle build --no-daemon || return 0

# 전체 소스 코드 복사
COPY . /app

# Gradle 빌드 수행
RUN ./gradlew clean build --no-daemon

# =============================== Start of Runtime Stage =============================== #
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/healthyLife-0.0.1-SNAPSHOT.jar /app/healthyLife-0.0.1-SNAPSHOT.jar

# 애플리케이션 실행
CMD ["java", "-jar", "healthyLife-0.0.1-SNAPSHOT.jar"]
