# 使用一个包含Java 17环境的基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将Maven打包好的jar文件复制到容器中
# ARG 命令定义了一个变量，我们可以在构建时传递给它
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 声明容器将监听的端口（仅为文档目的，实际端口映射在docker-compose中）
# EXPOSE 8081  <-- 每个服务的端口不同，可以不写或写对

# 容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "/app/app.jar"]