package net.rudikone.springbootsecurity.service;


import net.rudikone.springbootsecurity.model.Role;

import java.util.List;

public interface RoleService {

    public Role findRoleById(Integer id);

    public List<Role> findAll();
}
