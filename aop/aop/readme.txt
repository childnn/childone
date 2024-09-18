In Spring, an Advisor is an aspect that contains only a single advice object associated with a pointcut expression.

https://eclipse.dev/aspectj/
https://eclipse.dev/aspectj/doc/released/adk15notebook/index.html
https://github.com/eclipse-aspectj/aspectj/issues/79   Support JDK 17

1. idea 配置-参见截图
   在 Additional command line parameters 中添加：
   -source 1.8 -target 1.8
   不加好像也可以
2. 依赖插件: 配置 AspectJ 运行环境
  2.1. build.gradle.kts
  plugins {
      id("java")
      id("io.freefair.aspectj.post-compile-weaving") version "8.10" // 使用最新版本
  }
  build.gradle
  plugins {
      id "io.freefair.aspectj.post-compile-weaving" version "6.5.1" // 使用最新版本
  }

  dependencies {
      implementation 'org.aspectj:aspectjrt:1.9.7'
      implementation 'org.aspectj:aspectjweaver:1.9.7'
  }


  2.2. pom.xml
   aspectj-maven-plugin
  <build>
      <plugins>
          <plugin>
              <groupId>org.codehaus.mojo</groupId>
              <artifactId>aspectj-maven-plugin</artifactId>
              <version>1.14.0</version> <!-- 使用最新版本 -->
              <configuration>
                  <source>1.8</source> <!-- Java 版本 -->
                  <target>1.8</target>
                  <complianceLevel>1.8</complianceLevel>
                  <aspectLibraries>
                      <aspectLibrary>
                          <groupId>org.aspectj</groupId>
                          <artifactId>aspectjrt</artifactId>
                      </aspectLibrary>
                  </aspectLibraries>
              </configuration>
              <executions>
                  <execution>
                      <goals>
                          <goal>compile</goal>
                          <goal>test-compile</goal>
                      </goals>
                  </execution>
              </executions>
          </plugin>
      </plugins>
  </build>


3. 依赖
  //  include the aspectjweaver.jar to introduce advice to the Java class at load time:
  implementation("org.aspectj:aspectjweaver:1.9.19")
  // AspectJ runtime library aspectjrt.jar
  implementation("org.aspectj:aspectjrt:1.9.19")


  <dependencies>
      <dependency>
          <groupId>org.aspectj</groupId>
          <artifactId>aspectjrt</artifactId>
          <version>1.9.7</version> <!-- 使用最新版本 -->
      </dependency>
      <dependency>
          <groupId>org.aspectj</groupId>
          <artifactId>aspectjweaver</artifactId>
          <version>1.9.7</version> <!-- 使用最新版本 -->
      </dependency>
  </dependencies>



