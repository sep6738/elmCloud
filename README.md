# 软件服务工程2022级期末大作业

### 作者 zyh zly（名字首字母）
### 学号后4位 0176 0215
# 项目部署方法
### 1.打包
1.使用idea打包

![img.png](img%2Fimg.png)

2.使用mvn（在项目根目录运行：）
``
mvn clean package -DskipTests
``
### 2.docker部署
所有的配置文件都已经完成只需要在项目根目录运行：
``
docker-compose up --build -d
``
注：如果出现镜像拉取失败的问题可以科学上网或者换源，还是失败请根据docker-compose里面的镜像手动拉取
# 项目其他说明
本项目的api文档已经放在的根目录，分为下面几个版本：
- md版：elmCloudAPI.md
- openapi版：elmCloud.openapi.json
- apifox版：elmCloud.apifox.json（请导入apifox使用）
本项目默认运行在8080端口