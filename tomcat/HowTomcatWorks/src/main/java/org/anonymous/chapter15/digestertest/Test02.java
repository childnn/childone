package org.anonymous.chapter15.digestertest;

import org.apache.commons.digester3.Digester;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
// import org.apache.commons.digester.Digester;

public class Test02 {

  /*
   当调用 Digester 对象的 parse 方法的时候，它打开 XML 文档开始解析它。首先，
      Digester 看到了 employee 的开始元素。这样触发了关于 employee 模式的三个
      规则，第一个是创建一个对象，所以 Digester 初始化一个 Employee 类的对象，
      这样需要调用 Employee 类的构造函数，该构造函数打印出字符串“Creating Employee”。
      第二个规则设置 Employee 对象的属性，该元素有两个属性：firstName 和
      lastName。该规则调用方法这两个属性的 set 方法。这两个 set 方法打印出如下
      字符串：
      Setting firstName : Brian
      Setting lastName : May
      第三个规则调用 printName 方法，打印出如下内容：
      My name is Brian May
      接下来，最后两行诗调用 getFirstName 和 getLastName 方法的结果。
      First name : Brian
      Last name : May

   */
  public static void main(String[] args) {
    String path = System.getProperty("user.dir") + File.separator  + "etc";
    File file = new File(path, "employee2.xml");
    Digester digester = new Digester();
    // add rules
    digester.addObjectCreate("employee", "ex15.pyrmont.digestertest.Employee");
    digester.addSetProperties("employee");    
    digester.addObjectCreate("employee/office", "ex15.pyrmont.digestertest.Office");
    digester.addSetProperties("employee/office");
    digester.addSetNext("employee/office", "addOffice");
    digester.addObjectCreate("employee/office/address", 
      "ex15.pyrmont.digestertest.Address");
    digester.addSetProperties("employee/office/address");
    digester.addSetNext("employee/office/address", "setAddress"); 
    try {
      Employee employee = (Employee) digester.parse(file);
      ArrayList offices = employee.getOffices();
      Iterator iterator = offices.iterator();
      System.out.println("-------------------------------------------------");
      while (iterator.hasNext()) {
        Office office = (Office) iterator.next();
        Address address = office.getAddress();
        System.out.println(office.getDescription());
        System.out.println("Address : " + 
          address.getStreetNumber() + " " + address.getStreetName());
        System.out.println("--------------------------------");
      }
      
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }  
}
