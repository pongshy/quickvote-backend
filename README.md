## 微信小程序——快速投票小助手

快速投票小助手，

致力于为微信用户提供更好更方便的投票工具使用体验。

----

本项目为微信小诚寻——快速投票小助手的后端部分，使用Spring Boot的框架，并使用Maven进行依赖的管理，使用Mybatis实现数据库的持久化，以及使用Jenkins实现自动部署的功能。

**其中**，在本项目的**/src/main/resources/**下，拥有两个配置文件：application.yml，generator.xml。分别是项目整体的配置文件与Mybatis映射的配置文件。

application.yml：

```
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: xxxxxxxx
    password: xxxxxxx
    url: jdbc:mysql://xxxxxxx:xxxx/xxxx?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
  aop:
    proxy-target-class: true
  jackson:
    time-zone: GMT+8


server:
  servlet:
    context-path: /api
  port: 8080



mybatis:
  mapper-locations: classpath:mapping/*.xml
  type-aliases-package: com.shu.votetool.model.entity

#微信小程序相关配置
wx:
  AppId: xxxxxx
  secret: xxxxx
  template_id: xxxxxx
  page: xxxxxx
```

