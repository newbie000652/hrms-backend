FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 Jar 包到镜像中
COPY target/HRMS-0.0.1-SNAPSHOT.jar app.jar

# 暴露应用服务端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
