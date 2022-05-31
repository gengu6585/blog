一个简洁的个人博客系统（[博客演示](http://gengu.site:36116)）

- 使用框架：
  - 前端：BootStrap、layui
  - 后端：SpringBoot、Mybatis

- 版本依赖：

  Oracle-jdk-8

  Mysql-8.0.17

- 部署

  1. 本地打成jar包，并把jar包上传至服务器

     ```shell
     mvn clean package
     ```

  2. 部署数据库

     创建名为tale的数据库，执行sql/blog.sql文件。

  3. 修改配置文件application-dev.properties数据库连接信息，并把两个配置文件上传到jar包目录，如下：

     ![image-20220531174526395](README/image-20220531174526395.png)

  4. 使用docker部署或者在服务器上执行：

     ```shell
     java -jar my-blog-1.0.1-SNAPSHOT.jar
     ```

- 访问：

  - 前台：localhost
  - 后台：localhost/admin

