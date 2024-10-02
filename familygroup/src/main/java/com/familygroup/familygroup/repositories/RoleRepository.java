package com.familygroup.familygroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.familygroup.familygroup.models.Role;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(nativeQuery = true, value = "select * from role where role_description = 'USER';")
    public Role findUserRoleByName();
    
}
