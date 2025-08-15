# --- build stage ---
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 캐시 최적화: 의존성 먼저 받아서 레이어 캐시 활용
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x ./gradlew
# 의존성만 우선 받아 캐시(프로젝트에 따라 실패할 수 있어도 캐시가치가 커서 OK)
RUN ./gradlew --no-daemon build -x test || true

# 실제 소스 복사 후 빌드
COPY src ./src
RUN ./gradlew --no-daemon build -x test

# --- runtime stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app
# 필요 JAR 하나만 복사(여러 개면 정확한 이름으로 바꾸기)
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 7100
ENTRYPOINT ["java","-jar","/app/app.jar"]
