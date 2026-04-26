# ============================================================
# SCMS - Smart College Management System
# Docker Multi-Stage Build
# ============================================================

# Stage 1: Build stage - Compile and package the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

LABEL maintainer="SCMS Project Team"

WORKDIR /app

# Copy pom.xml first (leverage Docker layer caching)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy entire source code
COPY src ./src

# Build WAR file (skip tests for faster build)
RUN mvn clean package -DskipTests -B

# ============================================================
# Stage 2: Runtime stage - Run the application
FROM tomcat:10.1-jdk17-temurin

LABEL maintainer="SCMS Project Team"

# Remove default ROOT application
RUN rm -rf /usr/local/tomcat/webapps/ROOT \
    && rm -rf /usr/local/tomcat/webapps/ROOT.war

# Create logs directory for application logs
RUN mkdir -p /usr/local/tomcat/logs

# Copy built WAR from builder stage
COPY --from=builder /app/target/scms-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Set environment variables
ENV CATALINA_HOME=/usr/local/tomcat \
    JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC"

# Expose port
EXPOSE 8080

# Health check - verify Tomcat is running
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
    CMD curl -f http://localhost:8080/ || exit 1

# Start Tomcat
CMD ["catalina.sh", "run"]