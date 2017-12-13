package com.yizit.mes.service;

import com.yizit.mes.domain.Role;

import java.util.List;

/**
 * 角色模块
 */
public interface RoleService {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> listRole();

    Role saveOrUpdateRole(Role role);

    void removeRole(Long id);

    boolean hasChildren(Long pid);

}
