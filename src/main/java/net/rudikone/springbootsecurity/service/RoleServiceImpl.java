package net.rudikone.springbootsecurity.service;

import net.rudikone.springbootsecurity.model.Role;
import net.rudikone.springbootsecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Role findRoleById(Integer id) {
        return roleRepository.findById(id).get();
    }
}
