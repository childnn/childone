package org.anonymous.javasecurity;

import java.security.BasicPermission;
import java.security.PermissionCollection;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/17 14:39
 */
public class PermissionTest {
    public static void main(String[] args) {
        testImplies();
    }

    private static void testImplies() {
        MyPermission usaBp = new MyPermission("usa.*");
        MyPermission chinaBp = new MyPermission("china.*");
        MyPermission hubeiBp = new MyPermission("china.hubei.*");
        MyPermission wuhanBp = new MyPermission("china.hubei.wuhan.*");
        MyPermission wuchangBp = new MyPermission("china.hubei.wuhan.wuchang.*");

        // 通配符匹配
        System.out.println(chinaBp.implies(usaBp));
        System.out.println(hubeiBp.implies(wuchangBp));
        System.out.println(hubeiBp.implies(chinaBp));


        // 权限集合
        PermissionCollection perCollection = usaBp.newPermissionCollection();
        perCollection.add(chinaBp);
        System.out.println(perCollection.implies(hubeiBp));
    }
}

class MyPermission extends BasicPermission {

    public MyPermission(String name) {
        super(name);
    }
}