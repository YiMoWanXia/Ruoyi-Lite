# RuoYi-Lite

基于 [RuoYi](https://gitee.com/y_project/RuoYi) 的轻量级后端脚手架，在保留原有功能的前提下，对底层框架进行了现代化升级，并简化为单模块工程，方便快速二次开发和学习。

> **说明**：本项目仅做框架升级与工程结构精简，业务功能未做改动。

---

## ✨ 主要升级点

| 升级项 | 原版 | 当前 |
|--------|------|------|
| Spring Boot | 2.x | **3.5.8** |
| Java 版本 | 1.8 | **21** |
| ORM 框架 | MyBatis | **MyBatis Plus 3.5.17** |
| 工具类库 | 自建工具 | **Hutool 5.8.42** |
| JSON 处理 | Fastjson | **Fastjson2 2.0.61** |
| API 文档 | Swagger / Knife4j | **SpringDoc OpenAPI 2.8.16** |

---

## 🛠 技术栈

- **核心框架**：Spring Boot 3.5.8
- **安全框架**：Spring Security
- **ORM 框架**：MyBatis Plus 3.5.17 + MyBatis Plus Generator（代码生成器）
- **连接池**：Druid 1.2.28
- **缓存**：Redis
- **工具库**：Hutool 5.8.42
- **JSON 序列化**：Fastjson2 2.0.61
- **API 文档**：SpringDoc OpenAPI（Swagger UI）2.8.16
- **对象存储**：阿里云 OSS 3.18.5
- **系统信息**：OSHI 6.6.1
- **其他**：Lombok、Validation、AOP、UserAgentUtils

---

## 📦 功能模块

### 系统管理（system）
- 用户管理
- 角色管理
- 菜单管理
- 部门管理
- 岗位管理
- 字典管理
- 参数管理
- 通知公告
- 文件管理
- 登录 / 注册 / 个人信息

### 系统监控（monitor）
- 在线用户
- 登录日志
- 操作日志
- 缓存监控
- 服务器监控

### 代码生成（gen）
- 基于 MyBatis Plus Generator，支持前后端代码自动生成

### 基础能力（base）
- 统一响应封装与全局异常处理
- AOP 日志记录
- Spring Security + JWT 鉴权
- XSS 过滤、重复请求包装
- Redis 缓存与缓存管理
- 国际化（i18n）
- 通用工具类（文件、IP、日期、字符串、安全等）

---

## 🏗 项目结构

```
ruoyi-lite
├── sql/                        # 数据库脚本
│   └── ry_20260319.sql
├── src/main/java/com/ruoyi/lite/
│   ├── RuoyiLiteApplication.java
│   └── core/
│       ├── base/               # 基础框架：工具类、常量、异常、AOP、安全配置、过滤器、Redis 等
│       ├── gen/                # 代码生成器
│       └── module/             # 业务模块
│           ├── common/         # 通用接口（验证码等）
│           ├── monitor/        # 系统监控
│           └── system/         # 系统管理
├── src/main/resources/
│   ├── application.yml         # 主配置
│   ├── application-dev.yml     # 开发环境配置
│   ├── mybatis/                # Mapper XML
│   └── i18n/                   # 国际化资源
├── pom.xml
└── README.md
```

---

## 🚀 快速开始

### 环境要求

- JDK 21+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+

### 1. 初始化数据库

执行 `sql/ry_20260319.sql` 脚本，创建数据库并导入初始数据。

### 2. 修改数据库与缓存配置

编辑 `src/main/resources/application-dev.yml`：

```yaml
spring:
  datasource:
    druid:
      url: jdbc:mysql://127.0.0.1:3306/ruoyi-lite?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
      username: root
      password: 你的密码
  data:
    redis:
      host: 127.0.0.1
      port: 6379
      password: 你的密码
```

### 3. 启动项目

```bash
# 方式一：Maven 启动
mvn spring-boot:run

# 方式二：打包后启动
mvn clean package -DskipTests
java -jar target/ruoyi-lite-1.0.0.jar
```

### 4. 访问服务

- 后端接口：`http://localhost:8080`
- API 文档：`http://localhost:8080/swagger-ui.html`
- Druid 监控：`http://localhost:8080/druid/index.html`
  - 默认账号：`mysql` / `mysql1212.`（可在 `application-dev.yml` 中修改）

---

## ⚙️ 配置文件说明

| 文件 | 说明 |
|------|------|
| `application.yml` | 应用主配置，包含服务端口、MyBatis Plus、文件上传、国际化等 |
| `application-dev.yml` | 开发环境配置，包含数据库、Redis、日志、Druid、XSS、Referer 等 |
| `logback-spring.xml` | 日志配置 |

---

## 📝 注意事项

1. 本项目为单模块工程，去除了 RuoYi 原版的 `ruoyi-admin`、`ruoyi-common`、`ruoyi-system` 等多模块拆分，更适合轻量级项目和学习使用。
2. MyBatis Plus 已配置全局逻辑删除字段 `is_delete`，默认 `1` 为删除，`0` 为未删除。
3. 默认启用了 XSS 过滤，可通过 `application-dev.yml` 中的 `xss` 节点调整开关和排除路径。
4. Spring Boot 3.x 基于 Jakarta EE 命名空间，部分依赖和包名与 Spring Boot 2.x 不兼容，已在升级时统一调整。

---

## 📄 开源协议

本项目基于 RuoYi 衍生，遵循 MIT 开源协议。
