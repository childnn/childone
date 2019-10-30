package org.anonymous.maven;

/**
 * @author child
 * 2019/4/7 10:30
 * maven: apache-maven
 *   maven 是一款 项目管理工具(构建工具),专门用来管理项目
 *      同时也是一个 依赖管理工具和信息管理工具: 它提供中央仓库，能自动下载构件（jar包/插件等）
 *   在实际工作中，我们除了编写源代码(.java),我们有大量的时间花在了 编译,单元测试,生成文档,打包,部署等繁琐工作上,这一系列的关系项目生命周期的工作就是 【构建】
 *   maven 就是一款软件,用 命令 来让这一系列工作自动化 //maven 跨平台
 *   企业开发: 团队开发完的项目,都要交给 maven 管理
 *   maven 对于项目目录结构，测试用例命名方式等内容都有既定的规则，避免了不同项目/不同IDE/不同平台的不同规范 -- 约定优于配置（convention over configuration）
 *  作用:
 *     1.统一管理项目 jar 包: 配置文件(pom.xml) --> repository(仓库) --> jar 包
 *     2.为项目提供了大量的命令功能(管理项目的生命周期)
 *          项目生命周期: compile(编译) test(测试)  package(打包)  install(部署)  runtime(运行)
 *     3.对工程分模块构建,提高开发效率: 后面项目阶段会讲
 *    注：
 *        pom -- project object model (项目对象模型)
 *          maven 抽象了一个完整的构建生命周期模型,这个模型吸取了大量其他构建脚本和构建工具的优点(如 ant,make),总结了大量项目实际需求
 *              如果遵循这个模型,可以避免很多不必要的错误,可以使用大量成熟的 maven 插件来完成各种任务
 *        dependency -- 依赖管理模型
 *
 * repository: 仓库
 *   1.本地仓库
 *      工程第一次会远程中央仓库(互联网:http://repo1.maven.org/maven2/)去下载 jar 包
 *      将 jar 包存在本地仓库
 *      第二次不需要从远程中央仓库去下载,先从本地仓库找,
 *      如果找不到,才回去远程仓库找
 *   2.远程中央仓库
 *      就是远程仓库, 仓库中 jar 由专业团队(maven团队)统一维护
 *      中央仓库地址: http://repo1.maven.org/maven2/
 *   3.远程私服仓库
 *      公司内部自己架设的一台私服,用来放本公司自主研发的 jar 包
 *      一般不对外公开 自己公司内部下载使用
 *
 * 仓库 jar 包的坐标: -- 同一目录下,不会有同名的文件/文件夹 -- jar 包的唯一标识
 *     <dependency>
 *       <groupId>junit</groupId>
 *       <artifactId>junit</artifactId>
 *       <version>4.11</version>
 *       <scope>test</scope>
 *     </dependency>
 * groupId:
 *   定义当前 maven 项目隶属的实际项目(在 idea 中可以认为就是 项目与模块的关系)
 *   eg: SpringFranmework 这一实际项目,对应的 maven 项目会有很多,如 spring-core,spring-context 等
 *   即:一个实际项目分为很多 模块(module)
 *   groupId 通常与 Java 包名表示方式类似,域名反向一一对应: org.anonymous
 * artifactId:
 *   定义实际项目中的 maven 项目(module), 推荐做法是使用实际项目名作为 artifactId 的前缀, 这样做便于寻找实际构建
 *   eg: spring-core -- 即 spring 项目下的 core 模块
 *   默认情况下,maven 生成的构架,文件名以 artifactId 开头, 如 spring-core-verison.jar
 *   使用实际项目名作为前缀,就能方便从一个 lib 文件夹中找到某个项目的一组构建 -- 避免了不同的实际项目(groupId),但是 maven 项目(模块)同名(artifactId 同名)
 *    比如 不同公司的项目可能都有 core 模块
 * version:
 *   定义了 Maven 项目当前所处的版本
 * packaging:
 *   定义了 maven 项目的打包方式 jar/war 等 -- 不指定时 默认为 jar
 * classifier:
 *   定义构建输出的一些附属构建. 附属构建与主构件对应: 比如 xxx-javadoc.jar/xxx-sourves.jar
 *
 * 扩展:
 *    以后自己编写的项目,也会放在仓库 repository 中
 *      day00 -- 100 jar -- 项目 install 入仓库
 *      day01 -- 使用了与 day00 相同的 100 jar 包 -- 直接去仓库中找 day00 即可
 *
 * maven 安装:
 *   解压即安装
 *      目录:
 *          bin : maven 的二进制命令
 *          boot : maven 加载 第三方 jar 的支撑 -- maven 自己的类加载器框架 plexus-classwords
 *          conf: maven 配置文件 -- settings.xml
 *          lib: maven 运行时需要的 jar -- 真正的 "maven"
 *   配置环境变量: mvn -version
 *      MAVEN_HOME: 安装目录
 *      Path: %MAVEN_HOME%\bin;
 *   设置 配置文件: conf/settings.xml 53行, 设置本地仓库路径
 *      <localRepository>本地仓库目录</localRepository>
 *
 * 项目/模块名
 *      --> src
 *          --> main (web 项目)
 *              --> java (类似非 maven 项目的 src 目录: 蓝色) -- 主代码目录
 *              --> resources (配置文件目录)
 *              --> webapp (原 web 项目的 web 目录)
 *                  --> WEB-INF -- web.xml
 *          --> test (测试专用)
 *              --> java (绿色)   -- 测试代码目录
 *  项目的骨架:
 *      maven 规定的 基本目录结构(src 下的目录结构)和 pom.xml 的内容,称为项目的骨架
 *
 *  clean: 清除 target 目录
 *  compile: 编译项目
 *  package: 打包 web 项目为 war 包
 *  install: 将当前项目 按配置坐标 部署到 仓库
 *  deploy: 部署远程仓库
 *  test: 测试
 *
 *  依赖坐标:
 *      Maven是通过groupId、artifactId、version这三个类似于坐标的元素来确定唯一性的
 *      没有任何实际的Java代码，我们能够定义一个Maven项目的POM，这体现了Maven的一大优点，
 *      它能让项目对象模型最大程度地与实际代码相独立，我们可以称之为解耦。
 *      这在很大程度上避免了Java代码和POM代码的相互影响。只要我们定义的POM稳定后，日常的Java代码开发工作中基本不会涉及到POM的修改。
 *  <dependency>
 *      <groupId></groupId>   --- repository 仓库的主目录
 *      <artifactId></artifactId>  --- 项目名
 *      <version></version>  --- 版本
 *      <scope></scope> -- 依赖范围
 *  </dependency>
 *
 *  依赖范围:        编译有效     测试有效      运行有效    eg
 *      compile        √           √            √       spring-core
 *      test           -           √            -       junit
 *      provided       √           √            -       servlet-api (tomcat 自带，项目上线运行时不需要部署)
 *      runtime        -           √            √       jdbc 驱动
 *      system         √           √            -       本地的,maven仓库之外的类库
 * 依赖强度：
 *      compile > provided > runtime > test
 * 主要命令:
 *  mvn clean compile
 *  mvn clean test : test 之前会自动执行 compile
 *  mvn clean package依次执行了clean、resources、compile、testResources、testCompile、test、jar(打包)等７个阶段。 -- compile 之前会执行 test
 *  mvn clean install依次执行了clean、resources、compile、testResources、testCompile、test、jar(打包)、install等8个阶段。 -- install 之前会执行 package
 *  mvn clean deploy依次执行了clean、resources、compile、testResources、testCompile、test、jar(打包)、install、deploy等９个阶段。
 *
 * 由上面的分析可知主要区别如下，
 * package命令完成了项目编译、单元测试、打包功能（打包到 target 目录下），但没有把打好的可执行jar包（war包或其它形式的包）布署到本地maven仓库和远程maven私服仓库
 * install命令完成了项目编译、单元测试、打包功能，同时把打好的可执行jar包（war包或其它形式的包）布署到本地maven仓库，但没有布署到远程maven私服仓库
 * deploy命令完成了项目编译、单元测试、打包功能，同时把打好的可执行jar包（war包或其它形式的包）布署到本地maven仓库和远程maven私服仓库　　
 *
 * mvn clean：表示运行清理操作（会默认把target文件夹中的数据清理）；
 * mvn clean compile：表示先运行清理之后运行编译，会将代码编译到target文件夹中；
 * mvn clean test：运行清理和测试；
 * mvn clean package：运行清理和打包；
 * mvn clean install：运行清理和安装，会将打好的包安装到本地仓库中，以便其他的项目可以调用；
 * mvn clean deploy：运行清理和发布（发布到私服上面）。
 *
 *
 * make：与系统有关的 构建工具（不跨平台）。 有 makefile 语法
 * ant：
 *   another neat tool -- 另一个整洁的工具
 *   java 版本的 make （跨平台）
 *   使用 xml 第一构架脚本：build.xml
 * maven 可以运行在 jdk 1.4+ 版本,要使用 maven,必须安装 jdk
 */
public class MavenDemo {
}
