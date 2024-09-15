# lepao-backend

## 简介

`lepao-backend` 是一个基于 SpringBoot、Mybatis 和 MySQL 的智能匹配系统。它的主要目标是在校同学提供一个平台，帮助他们找到志同道合的小伙伴。系统采用随机匹配算法，优先根据相似标签进行匹配。

## 功能

- 用户注册与登录
- 用户信息管理
- 标签管理
- 智能匹配

## 技术栈

- SpringBoot
- Mybatis
- MySQL
- Maven

## 安装与运行

### 环境要求

- JDK 1.8 或更高版本
- MySQL 5.7 或更高版本
- Maven 3.6 或更高版本
- Redis 6.2 或更高版本
- SpringBoot 2.6.4 或更高版本

### 安装步骤

1. 克隆项目到本地

```bash
git clone https://github.com/kobewl/lepao-backend.git
```

2. 进入项目目录

```bash
cd lepao-backend
```

3. 修改 `application.properties` 文件，配置数据库连接信息

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lepao?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

4. 使用 Maven 构建项目

```bash
mvn clean install
```

5. 运行项目

```bash
java -jar target/lepao-backend.jar
```

### 访问

项目启动后，可以通过浏览器访问以下 swagger 文档地址进行测试：

```
http://localhost:8080/api/doc.html
```

## 随机匹配——距离优先算法

### 算法流程

1. 随机选择一个用户 A，优先使用用户 A 的标签作为参考，进行相邻距离标签匹配。
2. 从数据库中随机选择 N 个用户（N 可根据实际情况调整）。
3. 计算用户 A 与这 N 个用户的标签相似度。
4. 选择相似度最高的用户 B 作为匹配对象。
5. 如果相似度大于预设阈值，则将用户 A 和用户 B 进行匹配；否则，重新选择用户 A 并重复上述步骤。

### 相似度计算

标签相似度可以通过以下公式计算：

```
相似度 = (共同标签数量 / (用户 A 标签数量 + 用户 B 标签数量 - 共同标签数量))
```

## 贡献

如果你对这个项目感兴趣，欢迎贡献代码或提出建议。请遵循 [贡献指南](CONTRIBUTING.md)。