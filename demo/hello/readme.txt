https://blog.csdn.net/lcczpp/article/details/130854591


1. javac Hello.java
2. 修改 MANIFEST.MF
   增加一行
   Main-Class: Hello(启动类名)
3. 运行: jar -cvfm hello.jar MANIFEST.MF Hello.class
     c表示要创建一个新的jar包，
     v表示创建的过程中在控制台输出创建过程的一些信息，
     f表示给生成的jar包命名
     m 表示指定 MANIFEST.MF 文件
4. 运行: java -DfilePath=xxx.txt -jar hello.jar argsxxx

---
指定编译目录
1. javac Hello.java -d target
    该命令会将 Hello 及其依赖的类编译到 target 目录
2. cd target & jar -cvfm hello.jar META-INF/MANIFEST.MF *
  * 表示把当前目录下所有文件都打在jar包里
2.x.  jar -cvfm hello.jar META-INF/MANIFEST.MF Hello.class com   (或者用 * 代替打包当前目录的所有)
  最后一个com表示把com这个文件夹下的所有文件都打进jar包

---
引入外部的 jar
1. 附带 jar 编译
  javac -cp lib/hello.jar World.java -d target
  -cp 表示 -classpath，指的是把 x.jar 加入 classpath 路径下
2. 把 lib/* 复制到 target 目录下
3. 修改 MANIFEST.MF, 增加 Class-Path 参数
4. jar -cvfm world.jar META-INF/MANIFEST.MF *