package com.lnjhhhh.pojo;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 19:44
 * @Version 1.0
 */

public class SysRole implements GrantedAuthority, Serializable {

    private Integer id;
    private String roleName;
    private String roleDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
