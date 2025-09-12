package ait.cohort63.shop.service;

import ait.cohort63.shop.model.entity.Role;
import ait.cohort63.shop.repository.RoleRepository;
import ait.cohort63.shop.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleUser() {
        // Poluchaiem roli usera iz bd
        return roleRepository
                .findByTitle("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role User not found in Database"));
    }

}
