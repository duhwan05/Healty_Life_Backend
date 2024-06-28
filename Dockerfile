# =============================== Start of Build Stage =============================== #
FROM gradle:7.5.1-jdk17 AS build

# 소스 코드 복사
COPY --chown=gradle:gradle . /home/gradle/project

# 작업 디렉토리 설정
WORKDIR /home/gradle/project

# Gradle 빌드 수행
RUN gradle cleanBuildCache && gradle clean build --no-daemon --info

# =============================== Start of Application Stage =============================== #
FROM openjdk:17-jdk-slim

# 빌드 결과물 복사
COPY --from=build /home/gradle/project/build/libs/*.jar /app/app.jar

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 실행
CMD ["java", "-jar", "build/libs/healthyLife1.0.jar"]

