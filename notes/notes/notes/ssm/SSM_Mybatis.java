package org.ssm;

/**
 * @author child
 * 2019/4/9 19:46
 * 文档约束类型
 * DTD: Document Type Definition
 * XSD：xml schema definition
 *
 *  omg: object management group 对象管理组织
 *  corba: common object request broker architecture 公共对象请求代理体系结构
 *
 * jndi: Java naming and directory interface. java 命名和目录接口
 * jts: Java transaction service -- (ctm)componnet transaction monitor. 组件事务监听器
 * jta: Java transaction api
 * jca: Java connection architecture. Java 连接器架构
 * 资源池: connection pool. 创建和管理一个连接的缓冲池技术,这些连接准备好被任何需要它们的线程使用
 * 线程管理:
 *
 * SSM:
 *   springmvc: web 层  (控制层)
 *   spring: service 层(事务) (业务逻辑层)
 *   mybatis: dao 层  (数据持久层)
 *  框架： 项目的半成品
 *      将以前复杂的需要繁琐代码实现的功能进行封装，对外一套自己的方式也可以实现同样的功能
 *      提高企业的开发效率，减少开发时间
 *  浏览器：
 *      html
 *      css
 *      js
 *      jquery： js 框架
 *      ajax：异步 jackson -- 主要使用 jQuery 的 ajax
 *      json: 数据格式
 *      bootstrap: html+css+js
 *      jsp: el/jstl
 *  服务器:
 *      web 层（控制层） -- springmvc/struts2(基本淘汰)
 *          servlet
 *          (filter)
 *          listener
 *      service 层（业务逻辑层）
 *          事务 -- spring: 包括 springmvc/jdbcTemplate //spring 就是一个大管家,统筹整个 Javaweb
 *          业务逻辑
 *      dao 层（数据持久层） -- mybatis/hibernate/jdbcTemplate(小jdbc框架)
 *          jdbc： 连接Java 与 数据库
 *  数据库：
 *      mysql
 *      redis
 *      Oracle
 *
 * mybatis
 *      jdbc 基本步骤实现:
 *          1. 加载驱动
 *              Class.forName("cn.mysql.jdbc.Driver")
 *          2. 获取连接
 *              Connection con = DriverManager.getConnection(url, username, password);
 *          3. 编写 sql
 *          4. 获取 sql 执行者
 *              PreparedStatement preparedStatement = con.prepareStatement(sql);
 *          5. 执行 sql
 *              ResultSet rs = preparedStatement.executeQuery();
 *          6. 处理结果集
 *              while(rs.next()) {
 *                  rs.getXxx("字段名")
 *                  ...
 *              }
 *          7. 关闭资源
 *              con.close()//preparedStatement.close()//rs.close()
 *  问题:
 *      1. 数据库的 连接信息 硬编码到代码中  -- 配置文件
 *      2. sql 语句硬编码到代码中 -- 配置文件
 *      3. 步骤代码繁琐 -- 抽取封装工具类
 * 自定义框架: 对数据库进行查询封装
 *      准备: 库 -->表
 *      流程:
 *          使用者:
 *              数据库信息 (核心配置 -- SqlMapConfig.xml) -- 一个 -- 封装 Configuration 对象
 *                   -- 驱动, 数据库连接地址(url), 用户名, 密码
 *              sql 语句 (sql 配置文件 -- xxxMapper.xml) -- 一类 -- 封装 Mapper 对象
 *                   -- sql 语句
 *                   -- 指定封装的类型(全限定名 -> 反射)
 *              调用框架 api 做数据操作
 *          框架内部编写:
 *              封装 jdbc 实现数据库的操作
 *    最终只加载核心配置文件(把 sql 配置文件放在 核心配置文件中加载)
 *
 *  SqlSession: 接口 -- 面向接口
 *      多个实现类,用来操作不同的数据库
 *  SqlSessionFactory: 生产 SqlSession 实现类 -- 工厂模式
 *     创建 SqlSession 的实现类并传递封装好的 Configuration 对象
 *  SqlSessionFactoryBuilder :  -- 构建者模式
 *     1. 封装好的 Configuration 对象
 *     2. 创建 SqlSessionFactory 工厂并且传递 Configuration 对象
 *
 * 使用者:
 *   1. 创建 SqlSessionFactoryBuilder
 *   2. 调用 api 创建 SqlSessionFactory
 *   3. 使用 SqlSessionFactory 创建 SqlSession
 *   4. 使用 SqlSession 的方法, 操作 数据库
 *
 * 使用 mybatis 框架:
 *      数据库连接核心配置文件: SqlMapConfig.xml -- 一个
 *          核心配置文件的标签约束: (必须按顺序定义)
 *              The content of element type "configuration" must match
 *              "(properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,
 *                   reflectorFactory?,plugins?,environments?,databaseIdProvider?,mappers?)".
 *      sql 映射配置文件: xxxMapper.xml -- 一类
 *   api
 *     1.SqlSessionFactoryBuilder
 *          解析 xml 配置文件: 数据库连接核心配置文件(SqlMapConfig.xml) 和 sql 映射配置文件(xxxMapper.xml)
 *          创建 SqlSessionFactory 对象
 *          方法: build(InputStream is) -- 数据库连接核心配置文件的 输入流对象
 *     2. SqlSessionFactory
 *          创建 SqlSession 对象
 *          方法: openSession()
 *          特点: SqlSessionFactory 对象是一个线程安全对象
 *    单例对象:
 *       多个线程同时访问一个资源时,只有一个对象. 如 servlet
 *    多例对象:
 *       多个线程同时访问一个资源时, 一个线程对应一个对象. 如 request
 *    单例对象 在多线程中存在 线程危机(可能有共享数据)
 *      为了避免单例对象的线程危机,只要不存在操作共享数据即可
 *    3. SqlSession
 *      作用: 封装 jdbc 操作, 对数据库进行 crud 操作
 *      操作数据库的方法:
 *          传统方式: 通过 dao 层的实现类 -- 了解
 *              selectList: 查询列表
 *              selectOne: 查询单个对象/统计个数
 *              insert: 保存
 *              update: 更新
 *              delete: 删除
 *          代理对象: dao 层的接口 与其 抽象方法 -- 重点
 *              getMapper(接口.class): 生成 dao 接口的代理对象
 *          事务与资源操作
 *              commit: 提交事务
 *              close: 释放资源
 *   特点: SqlSession 是一个线程不安全的对象(多例对象)
 *
 * sql 映射配置文件的 参数深入:
 *    useGeneratedKeys="true" : 返回最后一次插入的 主键(id)
 *    keyProperty="id": 将 id 封装给 对象的 id
 *    具体见: D:\Develop\IDEA_Project\maven-project\mybatis_day02_a\src\main\resources\ContactMapper.xml
 *     获取最后一次 保存数据的 主键: 必须得保存之后再查询(主键是数据库自动生成)
 *   方式一:
 *  	需要使用 mysql 提供的独有函数: select last_insert_id() -- 改函数需要配合事务 使用(事务 commit(), 才会返回 最后一次的 id)
 *    一般配合 insert 使用
 * 	  配置:
 * 		<selectKey>: 获取主键的标签
 * 		order: 指定 该语句指定的查询顺序: BEFORE/AFTER
 * 			一般是 先插入 再查询 - 最后一条记录的 主键
 * 		resultType: 返回值类型
 * 		keyProperty: 将值封装给指定对象的指定属性
 *     该方法执行了 2 条 sql 数据: 与数据库交互两次
 *    方式二:
 *    	 mybatis 会将最后一次插入数据的主键 id 自动封装给对象
 *    	 但是默认是不开启的,需要开启 id 赋值功能
 *    	 useGeneratedKeys
 *  -->
 * 	<!--<insert id="saveContact" parameterType="org.anonymous.domain.Contact" >
 * 		获取最后一次插入的 id 属性值, 如果希望获取 插入之前的一个 id: order="BEFORE"
 * 		<selectKey order="AFTER" resultType="int" keyProperty="id">
 * 			select last_insert_id();
 * 		</selectKey>
 * 		insert into contact (name, sex, age, province_id, qq, email) values (#{name}, #{sex}, #{age}, #{province_id}, #{qq}, #{email});
 * 	</insert>-->
 *
 * <!--开启 id 赋值功能
 * 	useGeneratedKeys="true" : 默认false
 * 	keyProperty: 指定赋值的属性
 * 	该方法只会执行 一条 sql: 与数据库交互一次
 * -->
 *
 *  下面的 sql 会被解析为 预加载
 * 	<!--PreparedStatement pst = connection.prepareStatement("insert into contact (name, sex, age, province_id, qq, email) values (?, ?, ?, ?, ?, ?)")-->
 * 	<!-- mybatis 将 sql 解析为 占位符形式，使用 PreparedStatement，预加载
 * 	 	然后使用： pst.setInt(1, contact.getId());
 * 	 			.......
 * 	 			java.util.Date -> 数据库 java.sql.Timestamp
 * 	 		 pst.setTimestamp(n, new Timestamp(contact.getDate().getTime()));
 * 	 -->
 * 	<insert id="saveContact" parameterType="contact" useGeneratedKeys="true" keyProperty="id" >
 * 		insert into contact (name, sex, age, province_id, qq, email) values (#{name}, #{sex}, #{age}, #{province_id}, #{qq}, #{email})
 * 	</insert>
 *
 * day02 总结：
 *    ognl 表达式: object-graph navigation language 对象图导航语言: 主要使用 #,%,$
 *   1. mybatis 核心对象的作用，方法，特点
 *   2. 基于动态代理的方式 使用 mybatis 操作数据 -- 不需要 dao 接口的实现类  -- 重点
 *      细节1: 获取插入数据的主键 id
 *      细节2: 参数类型 parameterType
 *      细节3: 返回类型 resultType
 *      细节4: 对象属性和表字段的映射 resultMap (字段别名映射)
 *      <!--
 * 		 parameterType: 被代理对象方法的参数类型
 * 			1.pojo 对象: plain old java object. 就是一个 JavaBean 对象, 这个 pojo 对象不参与业务数据的封装,只用来封装数据库的数据
 * 				在参数中写  全限定名
 * 			2.基本数据类型: 包含 String, Integer, Long
 * 				在参数中, 写小写的 包装类类型 - string/int/long 或 包装类的 全限定名 java.lang.String/Integer/Long
 * 		 对应的 sql 语句:
 * 		 	pojo 对象: #{对象的属性}
 * 		 	基本数据类型: #{随便写}
 * 	    -->
 * 	    <!--删除1-->
 * 	    <delete id="deleteContact" parameterType="contact">
 * 		    delete from contact where id = #{id};
 * 	    </delete>
 * 	    <!--删除2-->
 * 	    <delete id="deleteContact0" parameterType="int">
 * 		    delete from contact where id = #{随便写};
 * 	    </delete>
 *
 * 	   <!--模糊查询
 * 	     1. #{随便写} : 解析为占位符, PreparedStatement 对象,预编译 -- 没有引号
 * 	     2. '%${...}%' : sql 拼接 statement 对象, 没有预编译处理, 有 sql 注入的风险 不建议使用 -- 需要引号
 * 		    sql 拼接: el 表达式  ${} 底层是 statement
 * 			    parameterType: pojo 对象类型 ${属性}
 * 							基本数据类型 ${value}
 * 	   预编译的优点:
 * 	      1. 使用占位符, 有效避免 sql 注入问题
 * 	      2. 预编译,即预先加载 sql 的语法, 提高编译后的 执行效率
 * 	    -->
 * 	    方式一: 占位符形式
 * 	    <select id="findLike" parameterType="string" resultType="org.anonymous.domain.Contact">
 * 		    select * from contact where name like #{随便写};
 * 	    </select>
 * 	    方式二: 有 sql 注入风险, 少用
 * 	    <select id="findLike" parameterType="string" resultType="contact">
 * 		    select * from contact where name like '%${value}%'; //注意时 el 表达式,不是 ognl 表达式
 * 	    </select>
 *
 *   3. 传统方式使用 mybatis 操作数据 -- 需要 dao 接口实现类(了解)
 *      传统方式必须使用 mybatis 提供的方法 --类似 jdbcTemplate
 *   4. 数据库核心配置文件(SqlMapConfig.xml)
 *      properties 标签 : 引入外部 .properties 配置文件 -- 数据库连接信息 -- 使用 el 表达式获取 相关 key 的 value 值
 *      typeAliases 标签: 给指定的 类全限定名起别名(一般就是 将 JavaBean 的全限定名简化为 类名)
 *          子标签:
 *              1) typeAlias: - 可以写多个,一个标签对应一个 JavaBean
 *                  属性: type - 类的全限定名
 *                       alias - 别名
 *              2) package: 通过 name 属性 指定整个包 -- org.anonymous.domain
 *                    整个包下的所有类 都有别名: 就是类名(不区分大小写)
 *      mappers 标签: 引入 sql 映射配置文件
 *          子标签:
 *              1) mapper: 可以写多个,一个对应一个 sql 映射配置文件
 *                  resource 属性: sql 映射配置文件名(相对路径)
 *                  class 属性:
 *                       使用条件: a) 配置文件 放在 resources 目录下的包中, 包名必须和 dao 相应接口所在的包名一致
 *                                b) 配置文件名 必须 和 dao 接口名一致
 *              2) package 引入整个包下的怕配置文件
 *                   name 属性: 包名
 *                     条件: 同 class 属性的使用条件
 *                     name 属性的包名 必须就是 dao 接口所在的 包
 *                     配置文件名 必须和 dao 接口名一致
 *  补充: javabean 的属性是对象时,怎么查 -- #{对象.属性}
 *
 *  day03:连接池,条件查询,多表查询
 *   回顾: mybatis 两种操作方式: 动态代理, 传统
 *   1.mybatis 自带数据库连接池
 *      数据库连接池: 内有 多个 connection 连接对象
 *          最大连接数: 正在和数据库交互的连接 (活跃区) -- 当需要和数据库交互的时候,从空闲区获取连接使用. 使用完毕则交还连接给 空闲区
 *          最大空闲连接数: 空闲连接 (空闲区) -- 项目启动,就会往该区放一部分连接对象
 *      mybatis 的连接对象,是在使用的时候才真正获取到,在创建(调用 openSession()方法时)不会获取到连接对象
 *   2.动态 sql -- 条件查询 与 sql 动态拼接  (sql 映射配置文件的标签)
 *       1) if 标签: sql 拼接的判断
 *       2) where 标签: 优化 sql 拼接 -- 替代 传统的 where true 子句
 *          -- where 标签会自动优化 第一个 if 标签下 条件成立 sql 对应的 and 关键字
 *      eg:
 *       <select id="findContact" resultType="contact" parameterType="contact">
 * 	        select id, name, sex, age, province_id, qq, email from contact
 * 	        <where>
 *              <if test="name != null and name != ''">
 * 		            and name = #{name}
 * 	            </if>
 * 	            <if test="sex != null and sex != ''">
 * 		            and sex = #{sex}
 * 	            </if>
 * 	            ....
 * 	        </where>
 *       </select>
 *       3). sql 标签, include 标签  -- 动态 sql 的进一步优化
 *          抽取 sql 语句 的公共部分,以便复用,
 *          使用 include 标签 通过 sql 标签的 id 属性值 引入
 *          eg:
 *          <sql id="selectSql"> //指定 id: 供引用
 * 	            select id, name, sex, age
 *          </sql>
 *
 *          <select id="findContact" resultType="contact" parameterType="contact">
 *
 * 	            <include refid="selectSql"/> //该标签即代表引入 sql 标签下的 sql 语句
 * 	                , province_id, qq, email from contact  //注意拼接过程中的 逗号, 空格等符号不要漏掉
 * 	            <where>
 * 	                <if test="name != null and name != ''">
 * 		                and name = #{name}
 * 	                </if>
 * 	                .... //多个 if 标签
 * 	            </where>
 *          </select>
 *        4)foreach 标签
 * 		        collection 属性:
 * 			        类型
 * 				        数组: array
 * 				        集合: list
 * 				        pojo 对象 : 对象属性 集合/数组 的属性名
 * 	            open: 开始符号 (
 * 	            close: 结束符号 )
 * 	            separator: 分隔符 逗号,
 * 	            item: 循环的每一个元素
 * 	            eg:
 * 	            <!--数组-->
 * 	            <select id="findById" parameterType="int[]" resultType="contact">
 * 		                select * from contact where id
 * 		             <foreach collection="array" open="in(" close=")" separator="," item="i">
 * 			            #{i}
 * 		             </foreach>
 * 	            </select>
 * 	            <!--list-->
 * 	            <select id="findByList" parameterType="list" resultType="contact">
 * 		                select * from contact where id in
 * 		            <foreach collection="list" open="(" close=")" separator="," item="i">
 * 			            #{i}
 * 		            </foreach>
 * 	            </select>
 * 	            <!--pojo 对象: plain ordinary Java object-->
 * 	            <select id="findByPojo" parameterType="POJOContact" resultType="contact">
 * 		                select * from contact where id in
 * 		            <foreach collection="abcd" open="(" close=")" separator="," item="i">
 * 			            #{i}
 * 		            </foreach>
 * 	           </select>
 *         5)映射标签: resultMap
 *              <!--映射: 做 Javabean 的属性和表的字段关联 - 要给表中的 字段定义别名时使用映射 - 多表关系时使用映射
 * 		            resultMap 映射标签
 * 			            id: 唯一 (一般用表明)
 * 			            type: 表对应的 javabean 全限定名(定义了 typeAliases 别名则可以使用别名: 即 类名)
 *     	            ps: 映射配置完毕,不影响全局, 谁用谁调用 - 根据 id 调用
 * 	             -->
 *
 * 	         //对象属性映射
 * 	            <resultMap id="contact" type="contact">
 * 		            <!--
 * 			            property: JavaBean的属性
 * 			            coLumn: property 对应的表中字段名(或 sql 语句中定义的别名)
 * 		            -->
 * 		            <!--主键映射 id-->
 * 		                <id property="id" column="id"/>
 * 		            <!--其他字段的映射-->
 * 		                <result property="name" column="姓名"/>
 * 		                <result property="sex" column="性别"/>
 * 		                <result property="age" column="年龄"/>
 * 		                <result property="province_id" column="省份"/>
 * 		                <result property="qq" column="qq"/>
 * 		                <result property="email" column="邮箱"/>
 * 		                <!--
 * 		                    association 对象属性映射(Javabean中的对象属性: 一对多表关系 --- 多的一方的 bean 中有一的 一方的 对象属性)
 * 		                        property : 属性名(JavaBean的成员变量名)
 * 		                        javaType: property 的类型(全限定名/typeAliases 标签定义的别名)
 * 		                    eg: 在 Account 中有用户属性: private User user
 * 		                        则: property="user" javaType="User类的全限定名(或简化别名 user)"
 * 		                -->
 * 		                <association property="user" javaType="全限定名">
 * 		                   <result property="对象属性名" column="表字段名"/>
 * 		                   ....
 * 		                </association>
 *                  </resultMap>
 *
 *               //集合属性映射
 * 		                <!--
 *                        <collection>: 集合属性映射
 *                              property: 属性名(集合变量名)
 *                              javaType: 对象类型
 *                              ofType: 集合泛型(全限定名)
 *                              -----------------------------------
 *                              resultMap: 引入 外部 resultMap (的 id)
 * 		                -->
 * 		                <!--映射-->
 *                  	<resultMap id="user" type="user">
 * 		                    <!--user 主键-->
 * 		                    <id property="id" column="id"/>
 * 		                    <!--user其他属性-->
 * 		                    <result property="username" column="username"/>
 * 		                    <result property="birthday" column="birthday"/>
 * 		                    <result property="sex" column="sex"/>
 * 		                    <result property="address" column="address"/>
 *
 * 		                <!--为user的 集合 属性映射 - collection 标签
 * 			                property : 属性名(变量名)
 * 			                javaType: 属性类型 - 全限定名/类名
 * 			                ofType: 集合泛型 - 全限定名/类名
 * 		                -->
 * 		                <!--账户映射-->
 * 		                <collection property="accounts" javaType="list" ofType="account">
 *
 * 			                <id property="id" column="aid"/>
 * 			                <result property="uid" column="uid"/>
 * 			                <result property="money" column="money"/>
 * 		                </collection>
 * 		                <!--角色映射: 内联 方式-->
 * 		                <collection property="roles" javaType="list" ofType="Role">
 * 			                <id property="rid" column="rid"/>
 * 			                <result property="roleName" column="role_name"/>
 * 			                <result property="roleDesc" column="role_desc"/>
 * 		                </collection>
 * 		                <!--对象属性 映射-->
 * 		                <!--<association property="" javaType=""/>-->
 * 	                 </resultMap>
 /
 /* 一对一------------<association>标签-------------------------------------------------------------------------------------------------------
 * 	  一: 一对一映射关系(对象属性): 如果 映射关系只需要用到一次, 可以使用  对象.属性  的方式进行映射, 如果 这种对象 映射需要复用,
 * 	            就必须定义 另外的 resultMap 了, 使用 嵌套方式, 或内联方式
 * 	        <resultMap type="Student" id="StudentWithAddressResult">
 *                  <id property="studId" column="stud_id" />
 *                  <result property="name" column="name" />
 *                  <result property="email" column="email" />
 *                  <result property="phone" column="phone" />
 *                  <result property="address.addrId" column="addr_id" />
 *                  <result property="address.street" column="street" />
 *                  <result property="address.city" column="city" />
 *                  <result property="address.state" column="state" />
 *                  <result property="address.zip" column="zip" />
 *                  <result property="address.country" column="country" />
 *          </resultMap>
 *          <select id="selectStudentWithAddress" parameterType="int" resultMap="StudentWithAddressResult">
 *                  SELECT STUD_ID, NAME, EMAIL, A.ADDR_ID, STREET, CITY, STATE, ZIP, COUNTRY
 *                  FROM STUDENTS S LEFT OUTER JOIN ADDRESSES A ON S.ADDR_ID=A.ADDR_ID
 *                  WHERE STUD_ID=#{studId}
 *          </select>
 *
 * 	 二: 嵌套方式 实现 一对一关系映射(对象属性)
 * 	      方式一(嵌套 select): 双 sql - 查询两次，把两次结果结合
 * 	           <resultMap type="Address" id="AddressResult">
 *                  <id property="addrId" column="addr_id" />
 *                  <result property="street" column="street" />
 *                  <result property="city" column="city" />
 *                  <result property="state" column="state" />
 *                  <result property="zip" column="zip" />
 *                  <result property="country" column="country" />
 *              </resultMap>
 *              <select id="findAddressById" parameterType="int" resultMap="AddressResult">
 *                  SELECT * FROM ADDRESSES WHERE ADDR_ID=#{id}
 *              </select>
 *
 *              <resultMap type="Student" id="StudentWithAddressResult">
 *                  <id property="studId" column="stud_id" />
 *                  <result property="name" column="name" />
 *                  <result property="email" column="email" />
 *                  //student 中的 address 属性, 嵌套 获取 id="findAddressById" 查询语句的结果
 *                  <association property="address" column="addr_id" select="findAddressById" />
 *              </resultMap>
 *              <select id="findStudentWithAddress" parameterType="int" resultMap="StudentWithAddressResult">
 *                  SELECT * FROM STUDENTS WHERE STUD_ID=#{Id}
 *              </select>
 *          在此方式中，<association>元素的 select 属性被设置成了 id 为 findAddressById 的语句。这里，两个分开的
 *          SQL 语句将会在数据库中执行，第一个调用 findStudentById 加载 student 信息，而第二个调用 findAddressById 来
 *          加载 address 信息。Addr_id 列的值将会被作为输入参数传递给 selectAddressById 语句。
 *
 *       方式二(嵌套resultMap): 单 sql
 *              <resultMap type="Address" id="AddressResult">
 *                  <id property="addrId" column="addr_id" />
 *                  <result property="street" column="street" />
 *                  <result property="city" column="city" />
 *                  <result property="state" column="state" />
 *                  <result property="zip" column="zip" />
 *                  <result property="country" column="country" />
 *              </resultMap>
 *              <resultMap type="Student" id="StudentWithAddressResult">
 *                  <id property="studId" column="stud_id" />
 *                  <result property="name" column="name" />
 *                  <result property="email" column="email" />
 *                  //引入 resultMap
 *                  <association property="address" resultMap="AddressResult" />
 *              </resultMap>
 *              <select id="findStudentWithAddress" parameterType="int" resultMap="StudentWithAddressResult">
 *                   SELECT STUD_ID, NAME, EMAIL, A.ADDR_ID, STREET, CITY, STATE, ZIP, COUNTRY
 *                   FROM STUDENTS S LEFT OUTER JOIN ADDRESSES A ON S.ADDR_ID=A.ADDR_ID
 *                   WHERE STUD_ID=#{studId}
 *              </select>
 *                  元素<association>被用来导入“有一个”(has-one)类型的关联。在上述的例子中，我们使用了<association>元素
 *                  引用了另外的在同一个 XML 文件中定义的<resultMap>。
 *
 *    三: 内联方式实现一对一映射: 直接在内部写出 映射关系
 *              <resultMap type="Student" id="StudentWithAddressResult">
 *                  //student 的简单属性
 *                  <id property="studId" column="stud_id" />
 *                  <result property="name" column="name" />
 *                  <result property="email" column="email" />
 *                  //student 的对象属性 address
 *                  <association property="address" javaType="Address">
 *                      <id property="addrId" column="addr_id" />
 *                      <result property="street" column="street" />
 *                      <result property="city" column="city" />
 *                      <result property="state" column="state" />
 *                      <result property="zip" column="zip" />
 *                      <result property="country" column="country" />
 *                  </association>
 *              </resultMap>
 *
 *  四: 扩展 resultMap
 *          我们可以从从另外一个<resultMap>，拓展出一个新的<resultMap>，这样，原先的属性映射可以继承过来，以实现。
 *           XML Code
 *          <resultMap type="Student" id="StudentResult">
 *              <id property="studId" column="stud_id" />
 *              <result property="name" column="name" />
 *              <result property="email" column="email" />
 *              <result property="phone" column="phone" />
 *          </resultMap>
 *          <resultMap type="Student" id="StudentWithAddressResult" extends="StudentResult">
 *              <result property="address.addrId" column="addr_id" />
 *              <result property="address.street" column="street" />
 *              <result property="address.city" column="city" />
 *              <result property="address.state" column="state" />
 *              <result property="address.zip" column="zip" />
 *              <result property="address.country" column="country" />
 *          </resultMap>
 *      id 为 StudentWithAddressResult 的 resultMap 拓展了 id 为 StudentResult 的 resultMap。
 *          如果你只想映射 Student 数据，你可以使用 id 为 StudentResult 的 resultMap,如下所示：
 *      XML Code
 *          <select id="findStudentById" parameterType="int" resultMap="StudentResult">
 *                  SELECT * FROM STUDENTS WHERE STUD_ID=#{studId}
 *          </select>
 *      如果你想将映射 Student 数据和 Address 数据，你可以使用 id 为 StudentWithAddressResult 的 resultMap：
 *      XML Code
 *          <select id="selectStudentWithAddress" parameterType="int" resultMap="StudentWithAddressResult">
 *                  SELECT STUD_ID, NAME, EMAIL, PHONE, A.ADDR_ID, STREET, CITY, STATE, ZIP, COUNTRY
 *                  FROM STUDENTS S LEFT OUTER JOIN ADDRESSES A ON S.ADDR_ID=A.ADDR_ID
 *                  WHERE STUD_ID=#{studId}
 *          </select>
 *
 * 一对多映射：------<collection>-------------------------------------------------------------------------------------------
 *  一个对象中,有另一个对象的对象集合 - Course --> Tutor : List<Course> courses;
 *   一. 嵌套方式 实现 一对多映射
 *      方式一(嵌套 resultMap): 单 sql
 *          <resultMap type="Course" id="CourseResult">
 *              <id column="course_id" property="courseId" />
 *              <result column="name" property="name" />
 *              <result column="description" property="description" />
 *              <result column="start_date" property="startDate" />
 *              <result column="end_date" property="endDate" />
 *          </resultMap>
 *          <resultMap type="Tutor" id="TutorResult">
 *              <id column="tutor_id" property="tutorId" />
 *              <result column="tutor_name" property="name" />
 *              <result column="email" property="email" />
 *              //引入 resultMap: course 映射
 *              <collection property="courses" resultMap="CourseResult" />
 *          </resultMap>
 *          //引入 tutor 映射
 *          <select id="findTutorById" parameterType="int" resultMap="TutorResult">
 *              SELECT T.TUTOR_ID, T.NAME AS TUTOR_NAME, EMAIL, C.COURSE_ID, C.NAME, DESCRIPTION, START_DATE, END_DATE
 *              FROM TUTORS T LEFT OUTER JOIN ADDRESSES A ON T.ADDR_ID=A.ADDR_ID LEFT OUTER JOIN COURSES C ON T.TUTOR_ID=C.TUTOR_ID
 *              WHERE T.TUTOR_ID=#{tutorId}
 *          </select>
 *      方式二(嵌套 select): 多 sql
 *          //course 映射
 *          <resultMap type="Course" id="CourseResult">
 *              <id column="course_id" property="courseId" />
 *              <result column="name" property="name" />
 *              <result column="description" property="description" />
 *              <result column="start_date" property="startDate" />
 *              <result column="end_date" property="endDate" />
 *          </resultMap>
 *          //address 映射
 *          <resultMap type="Address" id="AddressResult">
 *  *            <id property="addrId" column="addr_id" />
 *  *            <result property="street" column="street" />
 *  *            <result property="city" column="city" />
 *  *            <result property="state" column="state" />
 *  *            <result property="zip" column="zip" />
 *  *            <result property="country" column="country" />
 *  *       </resultMap>
 *          //tutor 映射: Address address; List<Course> courses;
 *          <resultMap type="Tutor" id="TutorResult">
 *              <id column="tutor_id" property="tutorId" />
 *              <result column="tutor_name" property="name" />
 *              <result column="email" property="email" />
 *
 *              //tutor 中的 address 对象: 引入映射 -- 直接引入
 *              <association property="address" resultMap="AddressResult" />
 *
 *              //<collection property="courses" resultMap="CourseResult" /> //方式一: 引入 映射
 *              //totur 中的 List<Course> courses 集合: 引入 select 语句的结果 -- 间接引入
 *              <collection property="courses" column="tutor_id" select="findCoursesByTutor" /> //方式二: 引入 sql
 *          </resultMap>
 *          //tutor: 指向 tutor 映射
 *          <select id="findTutorById" parameterType="int" resultMap="TutorResult">
 *              SELECT T.TUTOR_ID, T.NAME AS TUTOR_NAME, EMAIL
 *              FROM TUTORS T WHERE T.TUTOR_ID=#{tutorId}
 *          </select>
 *          //course: 指向 course 映射
 *          <select id="findCoursesByTutor" parameterType="int" resultMap="CourseResult">
 *               SELECT * FROM COURSES WHERE TUTOR_ID=#{tutorId}
 *          </select>
 *          //address: 指向 address 映射
 *          <select id="findAddressById" parameterType="int" resultMap="AddressResult">
 *               SELECT * FROM ADDRESSES WHERE ADDR_ID=#{id}
 *          </select>
 *  注: 嵌套 select 查询,涉及到 多个 sql, 多次查询, 降低了效率. 所以, 如果 某个 sql 不会被复用,
 *      则尽量使用 嵌套 resultMap 的 单 sql, 执行一次 sql, 效率较高
 *
 *  二: 也可以使用 内联方式 操作 一对多映射
 *      <resultMap id="" type="">
 *          <id property="" column=""/>
 *          <result property="" column=""/>
 *          ...
 *          //内联
 *          <collection property="属性" javaType="list(集合类型)" ofType="集合泛型">
 *              <id property="属性" column="字段"/>
 *              <result property="" column=""/>
 *              ...
 *          </collection>
 *      </resultMap>
 * ------------------------------------------------------------------------------------------------------------------------
 *  动态 sql : <if></if>, <choose></choose>, <where></where>, <foreach></foreach>, <trim></trim>
 *   一: if: 条件查询
 *     <resultMap type="Course" id="CourseResult">
 *          <id column="course_id" property="courseId" />
 *          <result column="name" property="name" />
 *          <result column="description" property="description" />
 *          <result column="start_date" property="startDate" />
 *          <result column="end_date" property="endDate" />
 *     </resultMap>
 *   //参数是 Map
 *     <select id="searchCourses" parameterType="hashmap" resultMap="CourseResult">
 *              SELECT * FROM COURSES
 *              WHERE TUTOR_ID= #{tutorId}
 *          <if test="courseName != null">
 *              AND NAME LIKE #{courseName}
 *          </if>
 *          <if test="startDate != null">
 *              AND START_DATE >= #{startDate}
 *          </if>
 *          <if test="endDate != null">
 *              AND END_DATE <= #{endDate}
 *          </if>
 *     </select>
 *  //SELECT * FROM COURSES WHERE TUTOR_ID= ? AND NAME like ? AND START_DATE >= ?
 *  Java code
 *      public interface CourseMapper {
 *          List<Course> searchCourses(Map<String, Object> map);
 *      }
 *      public void searchCourses() {
 *          Map<String, Object> map = new HashMap<String, Object>();
 *          map.put("tutorId", 1);
 *          map.put("courseName", "%java%");
 *          map.put("startDate", new Date());
 *          CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
 *          List<Course> courses = mapper.searchCourses(map);
 *          for (Course course : courses) {
 *              System.out.println(course);
 *          }
 *      }
 *
 *  二: choose, when -- 类似 if..else if... else // switch...case...default
 *      <select id="searchCourses" parameterType="hashmap" resultMap="CourseResult">
 *              SELECT * FROM COURSES
 *          <choose>
 *              <when test="searchBy == 'Tutor'">
 *                  WHERE TUTOR_ID= #{tutorId}
 *              </when>
 *              <when test="searchBy == 'CourseName'">
 *                  WHERE name like #{courseName}
 *              </when>
 *              <otherwise>
 *                  WHERE TUTOR start_date >= now()
 *              </otherwise>
 *          </choose>
 *      </select>
 *
 *  三: where 条件: 自动 优化 where 后的 and 关键字
 *      <select id="searchCourses" parameterType="hashmap" resultMap="CourseResult">
 *              SELECT * FROM COURSES
 *          <where>
 *              <if test=" tutorId != null ">
 *                  and TUTOR_ID= #{tutorId}
 *              </if>
 *              <if test="courseName != null">
 *                  AND name like #{courseName}
 *              </if>
 *              <if test="startDate != null">
 *                  AND start_date >= #{startDate}
 *              </if>
 *              <if test="endDate != null">
 *                  AND end_date <= #{endDate}
 *              </if>
 *           </where>
 *       </select>
 *
 *  四: trim 条件: 类似 where, 但比 where 更灵活
 *      <select id="searchCourses" parameterType="hashmap" resultMap="CourseResult">
 *              SELECT * FROM COURSES
 *           <trim prefix="WHERE" prefixOverrides="AND | OR">
 *                  <if test=" tutorId != null ">
 *                      TUTOR_ID= #{tutorId}
 *                  </if>
 *                  <if test="courseName != null">
 *                      AND name like #{courseName}
 *                  </if>
 *           </trim>
 *       </select>
 *      这里如果任意一个<if>条件为 true，<trim>元素会插入 WHERE,并且移除紧跟 WHERE 后面的 AND 或 OR
 *
 *  五: foreach 循环: 迭代遍历 数组/集合, 构造 and/or 条件 或 in 子句
 *     and/or
 *      <select id="searchCoursesByTutors" parameterType="map" resultMap="CourseResult">
 *             SELECT * FROM COURSES
 *          <if test="tutorIds != null">
 *              <where>
 *                  <foreach item="tutorId" collection="tutorIds">
 *                      OR tutor_id=#{tutorId}
 *                  </foreach>
 *              </where>
 *          </if>
 *      </select>
 *      //select * from courses where tutor_id = ? or tutor_id = ?..
 *    javacode
 *      public interface CourseMapper {
 *          List<Course> searchCoursesByTutors(Map<String, Object> map);
 *      }
 *      public void searchCoursesByTutors() {
 *          Map<String, Object> map = new HashMap<String, Object>();
 *          List<Integer> tutorIds = new ArrayList<Integer>();
 *          tutorIds.add(1);
 *          tutorIds.add(3);
 *          tutorIds.add(6);
 *          map.put("tutorIds", tutorIds);
 *          CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
 *          List<Course> courses = mapper.searchCoursesByTutors(map);
 *          for (Course course : courses) {
 *              System.out.println(course);
 *          }
 *      }
 *
 *   in:
 *      <select id="searchCoursesByTutors" parameterType="map" resultMap="CourseResult">
 *              SELECT * FROM COURSES
 *          <if test="tutorIds != null">
 *              <where>
 *                  tutor_id IN
 *              <foreach item="tutorId" collection="tutorIds" open="(" separator="," close=")">
 *                  #{tutorId}
 *              </foreach>
 *              </where>
 *          </if>
 *      </select>
 *      //select * from courses where tutor_id in (#{tutorId}, #{tutorId}..)
 *
 *  六: set 条件: 类似 where, 如果内部条件判断成立,拼接 sql
 *      <update id="updateStudent" parameterType="Student">
 *              update students
 *          <set>
 *              <if test="name != null">name=#{name},</if>
 *              <if test="email != null">email=#{email},</if>
 *              <if test="phone != null">phone=#{phone},</if>
 *          </set>
 *              where stud_id=#{id}
 *      </update>
 *      //update students set name=#{name}.. where stud_id = #{id}
 *
 *   CLOB: character large object 字符大对象(使用 char 来保存数据)- 数据库的数据类型(比如 xml 文档)
 *   BLOB: binary large object 二进制大对象(使用 二进制 来保存数据) - 比如 图片/音频/视频
 *
 *  3. 多表查询
 *   表与表的关系:
 *      一对多关系: 外键约束
 *      多对多关系: 中间表(中间表至少需要两个字段,这两个字段分别是两张表的主键)
 *              多对多关系,本质上就是两个一对多关系 (中间表就是 多)
 *    封装:
 *      1. 一个表对应一个 JavaBean
 *      2. 一对多表关系:
 *          对应的JavaBean实体中包含关系: 在一的一方有多的一方的集合, 在多的一方有一的一方的对象 //用户是一(属性 List<Account> accounts), 账户是多(属性 User user)
 *      3. 多对多表关系:
 *          对应 JavaBean 实体中包含关系: 相互有对方的集合 //用户与角色--一个用户可以有多个角色,一个角色可以对应多个用户
 *      4. JavaBean 中 对象属性的映射和list 集合属性的映射
 *    查询方式:
 *      账户和用户: (一对多的表关系)
 *          从账户的角度来说, 一个账户只属于一个用户, 这是一对一的查询方式
 *          从用户的角度来说, 一个用户可以有多个账户,这是一对多的查询方式
 *
 * day04:
 *  1.mybatis 延迟加载
 *      延迟加载: 就是延迟关联对象/集合数据的查询加载时间, 需要用到该数据时才加载,
 *          不需要用到该数据时就暂不加载,以提高查询效率,节约资源
 *      eg:
 *        Account 类中,有 User 属性, 在通过 左外连接查询两表关联信息时,如果我暂时只需要 account 表中的某些信息
 *        则 先查询 account 表中的信息即可满足要求, 后期有需要用到 user 表的信息时,再去查询 user 表 (而不用重新定义 sql 语句)
 *        此即 延迟加载(懒加载) 技术
 *       主体: accout
 *          操作: 查询所有账户
 *          sql: select * from account
 *        关联对象: user
 *           操作: (根据 account 外键= user 主键 配置延迟加载)
 *           sql: select * from user u where id = 41
 *        延迟加载的配置: 全局配置 -- 数据库核心配置文件(SqlMapConfig.xml)
 *        //默认情况下,没有延迟加载,只要 引入映射,就会执行 相应的 sql
 *          <!--全局环境配置: 延迟加载开关,默认是 false, 手动开启,设置为 true-->
 *          <settings>
 *              <!--所有关联对象的数据都会延迟加载-->
 *              <setting name="lazyLoadingEnabled" value="true"/>
 *          </settings>
 *      延迟加载好处: 先从单表查询,需要时再调用关联表的 sql 映射, 提高了数据库性能,因为查询单表要比查询多表效率高
 *      坏处: 因为只有当需要数据时,才会进行数据查询,这样在大批量数据查询时, 因为查询工作也要耗时,所以可能造成
 *          由于延迟加载的存在, 查询总时间变长(先查询 主 sql, 再去查询 从 sql,多次交互数据库)
 *
 *  2.mybatis 缓存 (面试)
 *      缓存: 内存中的一块 临时区域,可以用来存放内容
 *      作用: 提高查询效率
 *      一级缓存: sqlSession 级别的缓存
 *          用来存放数据,随着 sqlSession 的出生而出生,随着 sqlSession 的 销毁而销毁
 *          该一级缓存是 mybatis 自带的,是不可卸载的
 *          出生: sqlSession = sqlSessionFactory.openSession();
 *          销毁: 查询 sqlSession.close(); 或 增删改 sqlSession.commit();
 *          第一次查询的时候 -> 去一级缓存查找 -> 没有 -> 去数据库中查找 -> 存入 一级缓存  -- 执行一次 sql
 *          第二次查询的时候 -> 一级缓存中查询 -> 有 -> 直接获取 -- 不会再执行 sql
 *          后面查询的信息必须是 sqlSession 生命周期中 查询过的数据,才会从缓存中查,而不会执行 sql (close() 方法调用之前)
 *      二级缓存: sqlSessionFactory 级别的缓存 -- 多个 sqlSession 共享二级缓存
 *          sql 映射 级别的缓存, 多个 sqlSession 操作同一个 sql 映射文件 的 sql 语句时,
 *          多个 sqlSession 可以共用 二级缓存, 二级缓存时 跨 sqlSession 的.
 *          二级缓存 mybatis 不自带,需要配置
 *          配置:
 *            1).在 数据库连接核心配置文件(SqlMapConfig.xml) 文件中开启 二级缓存
 *              <settings>
 *                  //二级缓存开关: 默认就是开启的 -- 所以这一步可以省略
 *                  <setting name="cacheEnabled" value="true"/>
 *              </settings>
 *            2). 在 sql 映射配置文件中引入 二级缓存机制: 使用标签
 *                <cache/>
 *                表示当 不同的 sqlSession 来操作本 sql 映射配置文件中的 sql 语句时, 都可以使用 二级缓存
 *
 *                所有 <select> sql 的 查询结果都会缓存
 *                所有 <insert/>, <update/>, <delete/> 语句将会刷新缓存
 *                缓存根据 最近最少被使用(least recently used, LRU) 算法管理
 *                缓存不会被任何形式的基于时间表的刷新(没有刷新时间间隔), 即不支持定时刷新机制
 *                缓存将存储 1024 个 查询方法返回的列表或对象的引用
 *                缓存会当作一个 读/写 缓存, 这是指 检索的对象 不会被共享, 并且可以被调用者安全的修改, 不会被
 *                  其他潜在的调用者或者线程的潜在修改干扰(即, 缓存时线程安全的)
 *
 *            3). 配置了 二级缓存的 sql 映射配置文件中, 在需要被操作的 sql 标签中 设置: useCache="true"
 *               eg: <select id="" resultType="" parameterType="" useCache="true">
 *                      sql 语句
 *                   </select>
 *            4) sql 语句对应的 JavaBean 必须实现 serializable 接口
 *            5) 提交/关闭 前一个 sqlSession : sqlSession.commit()/close();
 *            前一个 sqlSession 必须关闭/提交才可能 将其中(一级缓存)中的数据 刷入 二级缓存
 *
 *  3.mybatis 注解 -- 核心/重点
 *      如果 同一个映射关系经常被用到, 可以定义一个 sql 映射配置文件, 其中 只写 sql 映射(不写具体的 sql, sql 还是用 .xml 替代): <resultMap id="" type="">映射关系</resultMap>
 *      注解实现 crud 操作
 *      create:
 *      @Insert()
 *          value: sql 语句 - @Insert("insert into user (username, sex, birthday, address) values(#{username}, #{sex}, #{birthday}, #{address})")
 *      @Options() 可选: 获取最后一条数据的主键 -- @Options(useGeneratedKeys = true, keyProperty = "id") //返回 最后一条数据的id
 *      delete
 *      @Delete()
 *           @Delete( "delete from user where id = #{随便写}")
 *      update:
 *      @Update()
 *           @Update( "update user set username = #{username} where id = #{id}")
 *      retrieve
 *      @Select()
 *           @Select( "select * from user where id = #{随便写}")
 *
 *   注解实现 多表关系的映射配置
 *      @Results() : value: @Result[] 数组
 *          @Select( "SELECT id uid, username name, sex xingbie, address dizhi from user")
 *          //别名映射 @Results 注解 相当于配置文件的 <resultMap>,
 *            //但是 @Results 不能设置 id, 这也就意味着 映射注解不能被重复使用,
 *           //此时, 可以 先在 sql 映射配置文件中 定义 <resultMap> 映射,
 *           // 在注解中, 使用 @ResultMap("sql 映射配置文件中的 namespace.id")
 *          @Results(
 *             value = {
 *                     //配置主键: id 默认 false
 *                     @Result( id = true, property = "id", column = "uid"),
 *                     //配置其他字段
 *                     @Result( property = "username", column = "name"),
 *                     @Result( property = "sex", column = "xingbie"),
 *                     @Result( property = "address", column = "dizhi")
 *                      }
 *                  )
 *    a)//对象映射与延迟加载: 账户中的用户属性- 主体是账户/对象是用户 -- 一对一查询
 *       用户 dao: @Select("select * from user ...")
 *       账户 dao:
 *      @Select( "select * from account ...")
 *      @Results(
 *          value = {
 *                  //自己的主键
 *                  @Result( id = true, property = "id", column = "id"),
 *                  //其他字段映射
 *                  @Result( property = "uid", column = "uid"),
 *                  @Result( property = "money", column = "money"),
 *                  //配置关联对象映射
 *                  * property: 关联的集合/对象的变量名
 *                  * javaType: 集合 Class //对象类型
 *                  * 配置文件中的 ofType 泛型不用写
 *                  * many: 配置一对多查询(一个用户对应多个账户)
 *                  one = @one(select="", fetchType=FetchType.LAZY/EAGER): 配置一对一查询(站在账户的角度: 一个账户对应一个用户)
 *                      * select : 引入的 sql
 *                      * fetchType: LAZY(延迟加载), EAGER(立即加载)
 *                  @Result( property = "关联对象的变量名(user)", javaType = 关联类.class(User.class), column = "对应的表中的字段(外键字段名:uid)",
 *                          one = @one(select = "namespace.id", fetchType = FetchType.LAZY)
 *
 *                  )
 *                 } -- 对应 value(
 *              ) -- 对应 @Results(
 *     b) //集合映射与延迟加载: 用户中的账户属性 -- 主体是用户/一个用户有多个账户(账户的list集合) -- 一对多查询
 *      账户 dao: @Select("select * from account where uid = #{随便写也是用户的id}")
 *      用户 dao:
 *       @Select( "select * from user")
 *     //配置账户属性映射: 整个 results 都是注解
 *     @Results(
 *             value = {
 *                     //自己的主键
 *                     @Result( id = true, property = "id", column = "id"),
 *                     //自己的其他字段
 *                     @Result( id = false, property = "username", column = "username"), //id 默认 false, 可以不写
 *                     @Result( id = false, property = "sex", column = "sex"),
 *                     @Result( id = false, property = "birthday", column = "birthday"),
 *                     @Result( id = false, property = "address", column = "address"),
 *
 *                     //配置 关联集合的映射: 参考 mybatis_day04_a 中的 两个 dao 配置文件
 *                     /*
 *                      * property: 关联的集合/对象的变量名
 *                      * javaType: 集合 Class //对象类型
 *                      * 配置文件中的 ofType 泛型不用写
 *                      * many: 配置一对多查询(一个用户对应多个账户) // one = @one(select="", fetchType=FetchType.LAZY/EAGER): 配置一对一查询(站在账户的角度: 一个账户对应一个用户)
 *                      * select : 引入的 sql
 *                      * fetchType: LAZY(延迟加载), EAGER(立即加载)
 *                      *
 *                      @Result( property = "accounts", javaType = List.class, column = "id",
 *                                   //引入 sql, 延迟加载
 *                                   many = @Many(select = "org.anonymous.dao.AccountDao.findAccountByUId", fetchType = FetchType.LAZY)
 *                        )
 *                   } -- 对应 value: result 注解的数组
 *        ) -- 对应 results 注解
 *        List<User> findUser(); //方法
 *     c) 动态 sql -- 引入 工具类的方法: 自定义 工具类
 *     //条件查询: 配置文件中对应 where/if 标签 -- 动态 sql
 *     /*
 *      * <select id="" resultType="" parameterType="">
 *      *      select * from user
 *      * 	<where>
 *      * 	<if test="name != null and name != ''">
 *      * 		and name = #{name}
 *      * 	</if>
 *      * 	<if test="sex != null and sex != ''">
 *      * 		and sex = #{sex}
 *      * 	</if>
 *      * 	</where>
 *      * </select>
 *      * @param user
 *      * @return
 *      * @SelectProvider: 引入别的类(工具类)
 *      *      属性 type: 引入类的字节码对象
 *      *      属性 method: 类中的方法名
 *      *
 *        @SelectProvider( type = SqlUtils.class, method = "selectSql") //执行 对应的 method 方法: 获取返回值(拼接的 sql)
 *        List<User> findUserByCondition(User user);
 *
 *
 */
public class SSM_Mybatis {
 /*
  * mybatis 一对多关系映射与注解
  * 方法一: 直接在注解中 写映射关系: @Results(@Result[] value();)
  *
  * @Select("select addr_id as addrId, street, city, state, zip, country from addresses where addr_id=#{id}")
  * Address findAddressById(int id); -- 接口的方法: 一对一
  *
  * @Select("select * from courses where tutor_id=#{tutorId}")
  * @Results(
  * {
  *     @Result(id = true, column = "course_id", property = "courseId"),
  *     @Result(column = "name", property = "name"),
  *     @Result(column = "description", property = "description"),
  *     @Result(column = "start_date" property = "startDate"),
  *     @Result(column = "end_date" property = "endDate")
  * })
  * List<Course> findCoursesByTutorId(int tutorId);
  *
  * @Select("SELECT tutor_id, name as tutor_name, email, addr_id FROM tutors where tutor_id=#{tutorId}")
  * @Results(
  * {
  *     @Result(id = true, column = "tutor_id", property = "tutorId"),
  *     @Result(column = "tutor_name", property = "name"),
  *     @Result(column = "email", property = "email"),
  *     //引入 sql
  *     @Result(property = "address", column = "addr_id", one = @One(select = " com.mybatis3.mappers.TutorMapper.findAddressById")),
  *     @Result(property = "courses", column = "tutor_id",many = @Many(select = "com.mybatis3.mappers.TutorMapper.findCoursesByTutorId"))
  * })
  * Tutor findTutorById(int tutorId); -- 接口的方法
  *
  * 这里我们使用了 @Many 注解的 select 属性来指向一个完全限定名称的方法，该方法将返回一个 List<Course> 对
  * 象。使用 column=”tutor_id”，TUTORS 表中的 tutor_id 列值将会作为输入参数传递给 findCoursesByTutorId()方法。
  * 在第三章，使用 XML 配置 SQL 映射器中我们讨论过，我们可以通过基于 XML 的映射器配置，使用嵌套结果 ResultMap
  * 来加载一对多关联的查询。而 MyBatis3.2.2 版本，并没有对应的注解支持。但是我们可以在映射器 Mapper 配置文件中配置<resultMap>并且使用@ResultMap 注解来引用它。
  * 在 TutorMapper.xml 中配置<resultMap>,如下所示：
  * XML Code
  * <mapper namespace="com.mybatis3.mappers.TutorMapper">
  *       <resultMap type="Address" id="AddressResult">
  *           <id property="addrId" column="addr_id" />
  *           <result property="street" column="street" />
  *           <result property="city" column="city" />
  *           <result property="state" column="state" />
  *           <result property="zip" column="zip" />
  *           <result property="country" column="country" />
  *      </resultMap>
  *
  *      <resultMap type="Course" id="CourseResult">
  *           <id column="course_id" property="courseId" />
  *           <result column="name" property="name" />
  *           <result column="description" property="description" />
  *           <result column="start_date" property="startDate" />
  *           <result column="end_date" property="endDate" />
  *     </resultMap>
  *
  *     <resultMap type="Tutor" id="TutorResult">
  *           <id column="tutor_id" property="tutorId" />
  *           <result column="tutor_name" property="name" />
  *           <result column="email" property="email" />
  *           //引入映射
  *           <association property="address" resultMap="AddressResult" /> //一对一关系映射
  *           <collection property="courses" resultMap="CourseResult" /> //一对多关系映射
  *     </resultMap>
  * </mapper>
  * Java Code
  * @Select("SELECT T.TUTOR_ID, T.NAME AS TUTOR_NAME, EMAIL,A.ADDR_ID, STREET, CITY, STATE, ZIP, COUNTRY, COURSE_ID, C.NAME,DESCRIPTION, START_DATE, END_DATE
  *        FROM TUTORS T
  *        LEFT OUTER JOIN ADDRESSES A
  *        ON T.ADDR_ID=A.ADDR_ID
  *        LEFT OUTER JOIN COURSES C
  *        ON T.TUTOR_ID=C.TUTOR_ID
  *        WHERE T.TUTOR_ID=#{tutorId}")
  *        //引入 映射
  * @ResultMap("com.mybatis3.mappers.TutorMapper.TutorResult")
  * Tutor selectTutorById(int tutorId); -- 接口的方法
  */
 /**
  * 动态 sql 与 注解
  * @InsertProvider(type=SqlUtils.class, method="方法名") -- 通过自定义工具类的方法 获取 sql
  * @UpdateProvider(...)
  * @DeleteProvider(...)
  * @SelectProvider(...)
  *
  * 查: @SelectProvider()
  *
  * public class TutorDynaSqlProvider { -- SQL 工具类
  *    public String selectTutorById() {
  *         return new SQL() {{
  *              SELECT("t.tutor_id, t.name as tutor_name, email");
  *              SELECT("a.addr_id, street, city, state, zip, country");
  *              SELECT("course_id, c.name as course_name, description, start_date, end_date");
  *              FROM("TUTORS t");
  *              LEFT_OUTER_JOIN("addresses a on t.addr_id=a.addr_id");
  *              LEFT_OUTER_JOIN("courses c on t.tutor_id=c.tutor_id");
  *              WHERE("t.TUTOR_ID = #{id}"); }} .toString();
  *    }
  * }
  * public interface TutorMapper { -- 接口
  *      @SelectProvider(type = TutorDynaSqlProvider.class, method = "selectTutorById") -- 调用 sql 工具类的方法
  *      @ResultMap("com.mybatis3.mappers.TutorMapper.TutorResult") -- 调用 xml 的 映射关系
  *      Tutor selectTutorById(int tutorId);
  * }
  * 由于没有支持使用内嵌结果 ResultMap 的一对多关联映射的注解支持，我们可以使用基于 XML 的<resultMap>配置，然后与@ResultMap 映射。
  * XML Code
  * <mapper namespace="com.mybatis3.mappers.TutorMapper">
  *     //映射一: address
  *      <resultMap type="Address" id="AddressResult">
  *           <id property="id" column="addr_id" />
  *           <result property="street" column="street" />
  *           <result property="city" column="city" />
  *           <result property="state" column="state" />
  *           <result property="zip" column="zip" />
  *           <result property="country" column="country" />
  *      </resultMap>
  *      //映射二: course
  *      <resultMap type="Course" id="CourseResult">
  *           <id column="course_id" property="id" />
  *           <result column="course_name" property="name" />
  *           <result column="description" property="description" />
  *           <result column="start_date" property="startDate" />
  *           <result column="end_date" property="endDate" />
  *      </resultMap>
  *      //映射三:
  *      <resultMap type="Tutor" id="TutorResult">
  *           <id column="tutor_id" property="id" />
  *           <result column="tutor_name" property="name" />
  *           <result column="email" property="email" />
  *           //引入 映射一:  一对一映射关系 - 一个 tutor 有 一个 address - Address address
  *           <association property="address" resultMap="AddressResult" />
  *           //引入 映射二: 一对多映射关系 - 一个 tutor 有 多个 course - List<Course> courses
  *           <collection property="courses" resultMap="CourseResult"/>
  *       </resultMap>
  * </mapper>
  * 使用了动态的 SQL provider，我们可以取得讲师及其地址和课程明细。
  *
  * 曾: @InsertProvider()
  *
  *    public class TutorDynaSqlProvider { -- 工具类
  *        public String insertTutor(final Tutor tutor) {
  *           return new SQL() {{
  *               INSERT_INTO("TUTORS");
  *               if (tutor.getName() != null) {
  *                   VALUES("NAME", "#{name}");
  *               }
  *               if (tutor.getEmail() != null) {
  *                   VALUES("EMAIL", "#{email}");
  *               }
  *        }} .toString();
  *      }
  * }
  * public interface TutorMapper { -- 接口
  *     @InsertProvider(type = TutorDynaSqlProvider.class, method = "insertTutor")
  *     @Options(useGeneratedKeys = true, keyProperty = "tutorId")
  *     int insertTutor(Tutor tutor);
  * }
  *
  * 改: @UpdateProvider()
  *
  *    public class TutorDynaSqlProvider { -- 工具类
  *        public String updateTutor(final Tutor tutor) {
  *            return new SQL() {{
  *               UPDATE("TUTORS");
  *              if (tutor.getName() != null) {
  *                  SET("NAME = #{name}");
  *              }
  *              if (tutor.getEmail() != null) {
  *                  SET("EMAIL = #{email}");
  *              }
  *              WHERE("TUTOR_ID = #{tutorId}");
  *              }} .toString();
  *       }
  *   }
  *   public interface TutorMapper { -- 接口
  *      @UpdateProvider(type = TutorDynaSqlProvider.class, method = "updateTutor")
  *      int updateTutor(Tutor tutor);
  *   }
  *
  * 删除: @DeleteProvider()
  *
  * public class TutorDynaSqlProvider { -- sql 工具类
  *    public String deleteTutor(int tutorId) {
  *       return new SQL() {{
  *          DELETE_FROM("TUTORS");
  *          WHERE("TUTOR_ID = #{tutorId}");
  *       }} .toString();
  *   }
  * }
  * public interface TutorMapper { -- 接口
  *    @DeleteProvider(type = TutorDynaSqlProvider.class, method = "deleteTutor")
  *    int deleteTutor(int tutorId);
  * }
  *
  */


}
