package com.familygroup.familygroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.familygroup.familygroup.models.Group;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long> {
    
}
