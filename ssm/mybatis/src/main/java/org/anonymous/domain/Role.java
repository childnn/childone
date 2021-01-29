package org.anonymous.domain;

/**
 * @author child
 * 2019/4/11 22:04
 */
public class Role {
    private Integer rid;
    private String roleName;
    private String roleDesc;

    @Override
    public String toString() {
        return "Role{" +
                "rid=" + rid +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
