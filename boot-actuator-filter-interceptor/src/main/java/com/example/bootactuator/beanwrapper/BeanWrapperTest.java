package com.example.bootactuator.beanwrapper;

import com.example.bootactuator.beans.Company;
import com.example.bootactuator.beans.Employee;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/15 8:44
 * @see org.springframework.beans.BeanWrapper
 * 1. 不与 lombok 兼容
 * 2. 属性必须有 getter/setter, 除非 设置 ignoreUnknown = true
 *  ignoreUnknown 表示属性无效
 *  ignoreInvalid 表示属性存在, 值为 null
 */
public class BeanWrapperTest {

    public static void main(String[] args) {
        Company c = new Company();

        BeanWrapperImpl bwComp = new BeanWrapperImpl(c);
        // setting the company name
        bwComp.setPropertyValue("name", "Some Company Inc.");
        // can also be done like this
        PropertyValue v = new PropertyValue("name", "Som Company Inc11.");
        bwComp.setPropertyValue(v);
        // bwComp.setPropertyValues(pvs);

        Employee em = new Employee();
        BeanWrapperImpl bwE = new BeanWrapperImpl(em);
        // bwE.setPropertyValue("name", "jim");
        bwE.setPropertyValue("salary", 12.0F);

        bwComp.setPropertyValue("managingDirector", em);

        Float salary = (Float) bwComp.getPropertyValue("managingDirector.salary");

        System.out.println("salary = " + salary);

    }

}
