package com.app.ims.repository;

import com.app.ims.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String role);
}
