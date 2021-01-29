package org.anonymous.test.mybatis;

import org.anonymous.domain.Account;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.apache.ibatis.reflection.ReflectorFactory
 * @see org.apache.ibatis.reflection.MetaObject
 * @see org.apache.ibatis.reflection.MetaClass
 * @see org.apache.ibatis.reflection.wrapper.ObjectWrapper -- 包装对象
 * @see org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory
 * @see org.apache.ibatis.reflection.Reflector -- 缓存实体类的 getter/setter 方法
 * @see org.apache.ibatis.reflection.ReflectorFactory -- 缓存/构建 Reflector 对象
 * ---
 * @see org.apache.ibatis.reflection.property.PropertyTokenizer
 * @since 2020/12/16 16:13
 */
public class SystemMetaObjectTest {
    @Test
    public void test() {
        MetaObject metaObject = SystemMetaObject.forObject(new Account());
        metaObject.hasSetter("");
    }

    @Test
    public void metaClass() {
        MetaClass.forClass(Account.class, new DefaultReflectorFactory());
    }
}
