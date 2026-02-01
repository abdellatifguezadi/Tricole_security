package org.tricol.supplierchain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tricol.supplierchain.dto.response.RoleDisplayResponse;
import org.tricol.supplierchain.mapper.UserMapper;
import org.tricol.supplierchain.repository.RoleRepository;
import org.tricol.supplierchain.service.inter.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public List<RoleDisplayResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(userMapper::toRoleDisplayResponse)
                .collect(Collectors.toList());
    }
}

