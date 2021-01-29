package org.anonymous.dao.annotation;

import org.anonymous.domain.User;
import org.anonymous.util.SqlUtils;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

/**
 * @author child
 * 2019/4/12 20:06
 * <p>
 * 二级缓存全局配置 SqlMapConfig.xml - <setting name="cacheEnabled" value="true"/> 默认开启
 * @see org.apache.ibatis.annotations.CacheNamespace 配置 Mapper 级别的二级缓存, 需要手动加
 * @see org.apache.ibatis.annotations.Options#useCache() 配置方法级别的二级缓存, 默认开启
 * @see org.apache.ibatis.annotations.Options.FlushCachePolicy#TRUE 强制刷新一级缓存
 */
@CacheNamespace // 二级缓存
public interface UserDao {

    //-------------------------------------------------------------------------------------------
    //  in 关键字段的使用 beginning..
    //-------------------------------------------------------------------------------------------

    /**
     * 关于 mybatis in 关键字的使用:
     * 1. 方法参数使用 id 集合的 String 类型: "1, 2, 3"
     * 要想注解获取到 参数中的值, 有两种方式:
     * 1.1. 固定格式   in (${_parameter))   --- 不能变, 不能加 @Param 注解.
     * 1.2. 方法参数上加 @Param("ids") -- 不能使用 1.1 的方式表示
     *
     * @param ids
     * @return
     */
    // @Select("select * from user where id in (${_parameter})")
    @Select("select * from user, (select * from user where id in (${ids})) t where t.id in (${param1})")
    // Parameter 'aaa' not found. Available parameters are [ids, param1]
    List<Map<String, Object>> selectIn(@Param("ids") String ids);

    @Select({
            "<script>",
            "select",
            "id, username",
            "from user",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Map<String, Object>> selectIn$(@Param("ids") Integer... ids);

    //-------------------------------------------------------------------------------------------
    //  in 关键字段的使用 ending..
    //-------------------------------------------------------------------------------------------

    /**
     * 保存用户: @Insert(value="sql")
     * value: 支持 ognl 表达式
     *
     * @param user
     */
    @Insert("insert into user (username, sex, birthday, address) values(#{username}, #{sex}, #{birthday}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    //返回 最后一条数据的id
    void saveUser(User user);

    //更新
    @Update("update user set username = #{username} where id = #{id}")
    void updateUser(User user);

    //删除
    @Delete("delete from user where id = #{随便写}")
    void deleteUserById(int id);

    /**
     * useCache = true -- 表示开启二级缓存, 默认 true.
     * ---
     * xml:
     * 1. mapper: <select flushCache="true"/> 刷新一级缓存
     * 2. config: <setting name="localCacheScope" value="STATEMENT"/>
     * 注解: flushCache = Options.FlushCachePolicy.TRUE 刷新一级缓存
     *
     * @see org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()
     */
    // @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @Select("select * from user where id = #{随便写}")
    User findById(int id);

    // mybatis 不支持 重载: 参见 org.anonymous.config.mybatis.spring.MyBatisConfig--mybatis-statement-id 生成策略.
    // @Select("select * from user where id = 1")
    // User findById();

    //模糊查询
    //like 后的 el 必须要引号: 因为不是占位符
    @Select("SELECT * FROM user WHERE username LIKE '%${value}%'")
    //el 表达式: 非占位符的形式/没有预编译(底层是 statement),有 sql 注入风险
    List<User> findLike(String username);

    //ognl 不需要引号: 是占位符
    @Select("SELECT * FROM user WHERE username LIKE #{随便写}")
    //ognl 表达式: 占位符形式 ? /preparedStatement 预编译
    List<User> findLike1(String username);

    //统计数量
    @Select("SELECT COUNT(*) FROM user")
    int count();

    /*
     * 注解与映射 1
     */
    @Select("SELECT id uid, username name, sex xingbie, address dizhi from user")
    //别名映射： @Results 注解 相当于配置文件的 <resultMap>,
    //但是 @Results 不能设置 id, 这也就意味着 映射注解不能被重复使用,
    //此时, 可以 先在 sql 映射配置文件中 定义 <resultMap> 映射,
    // 在注解中, 使用 @ResultMap("sql 映射配置文件中的 namespace.id")
    @Results(
            value = {
                    //配置主键: id 默认 false
                    @Result(id = true, property = "id", column = "uid"),
                    //配置其他字段
                    @Result(property = "username", column = "name"),
                    @Result(property = "sex", column = "xingbie"),
                    @Result(property = "address", column = "dizhi")
            }
    )
    List<User> findAll();

    /**
     * 注解与映射 2: 用户与账户: 一(用户)对多(账户)
     *
     * @return
     */
    @Select("select * from user")
    //配置账户属性映射
    @Results(
            //id = "可供复用",
            value = {
                    //自己的主键
                    @Result(id = true, property = "id", column = "id"),
                    //自己的其他字段
                    @Result(id = false, property = "username", column = "username"), //id 默认 false, 可以不写
                    @Result(id = false, property = "sex", column = "sex"),
                    @Result(id = false, property = "birthday", column = "birthday"),
                    @Result(id = false, property = "address", column = "address"),

                    //配置 关联集合的映射: 参考 mybatis_day04_a 中的 两个 dao 配置文件
                    /*
                     * property: 关联的集合/对象的变量名
                     * javaType: 集合 Class //对象类型
                     * 配置文件中的 ofType 泛型不用写
                     * many: 配置一对多映射(一个用户对应多个账户)
                     * select : 引入的 sql
                     * fetchType: LAZY(延迟加载), EAGER(立即加载)
                     */
                    /*
                     * 对于一对一映射:(对象属性: 一个账户中,只有一个 用户的对象属性)
                     *  使用: @Result(property="对象属性", column="字段", one = @One(select = "namespace.id"))
                     */
                    //方法执行时, id 的 值会作为参数传递给 findAccountById(id) 方法, 将得到的 List<Account> 返回
                    @Result(property = "accounts", javaType = List.class, column = "id",
                            //引入 sql(使用 namespace.id: 全限定名.方法名), 并开启延迟加载
                            many = @Many(select = "org.anonymous.dao.AccountDao.findAccountByUId", fetchType = FetchType.LAZY)
                    )
            }
    )
    List<User> findUser();

    //条件查询: 配置文件的 where/if 标签 -- 动态 sql
    /*
     * <select id="" resultType="" parameterType="">
     *      select * from user
     * 	<where>
     * 	<if test="name != null and name != ''">
     * 		and name = #{name}
     * 	</if>
     * 	<if test="sex != null and sex != ''">
     * 		and sex = #{sex}
     * 	</if>
     * 	</where>
     * </select>
     * @param user
     * @return
     */
    //    @Select("select * from user")

    /**
     * @SelectProvider: 引入别的类(工具类)
     * type: 引入类的字节码对象
     * method: 类中的方法名
     */
    @SelectProvider(type = SqlUtils.class, method = "selectSql")
    //执行 对应的 method 方法: 获取返回值(拼接的 sql)
    List<User> findUserByCondition(User user);

}
