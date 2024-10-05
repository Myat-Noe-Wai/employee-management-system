package net.javaguides.springboot.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
    
    public Role getRoleById(Long id) {
    	return roleRepository.findById(id).orElseThrow();
    }
    
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
    
    public void updateRole(Long id, Role roleDetails) {
    	Role role = roleRepository.findById(id).orElseThrow();
    	role.setName(roleDetails.getName());
    	role.setDescription(roleDetails.getDescription());
    	roleRepository.save(role);
    }
}

