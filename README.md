# Springboot 通用工程构建

### 简介

* 一、项目持久层采用 Mybatis-plus 有自动生成三层架构和Mapper方法：MybatisPlusGeneratorUtils
模板文件在resources/source/ 下，里面定义了公用的方法。

    可以手动写Mapper的方法，也可以使用MybatisPlus 自带的方法 basemapper，其中有
    很多自带的方法和快捷查询方法，类似于mybatis + hibernate的结合体

* 二、采用redis缓存，工具包：RedisServiceUtils,采用@Autowired、@Resource注入

* 三、SwaggerUI接口调试：http://localhost:8889/swagger-ui.html 

* 四、集成Shiro安全框架做安全验证，通过：CurrentUser()获取当前登录用户信息，CurrentUser.id()获取id，
其他属性可在 CurrentUser.class 中进行配置

* 五、1.防XSS渗透注入，重写参数传递方法，采用Jsoup过滤页面特殊字符。
2.防CSRF,通过接口公共参数做验证：
resources/source/controllerTemp.java.vm  模板中，每个方法都带有 RequestModel 参数。
验证规则：utils/SignUtils 方法

    也可放在header中请求，拦截器拦截验证
       
* 六、登录、退出登录方法：LoginController，shiro验证登录，加入验证码，时效性一分钟
前端将用户名和密码进行加密：Base64(login_token + 密码)，后端解密后进行登录验证

* 七、存在Log注解可以将操作信息写入到log表

* 八、现已使用数据库脚本存放在：resources/dbsql/ 下

* 九、

### 引用包

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)

