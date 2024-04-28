package com.tranv.d7shop.services.impl;

import com.tranv.d7shop.models.Role;
import com.tranv.d7shop.repository.RoleRepository;
import com.tranv.d7shop.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
