前提: 配置 MAVEN_HOME, JAVA_HOME(1.8)

源码: src/main/java/
将逻辑写在: DemoApplication#file 方法即可, 方法参数就是接口请求参数

打包运行:
1. 解压, 根目录下运行
   mvn clean package
2. cd target
3. java -jar demo-0.0.1-SNAPSHOT.jar
4. 浏览器访问: localhost:9999/file/[替换文件路径]