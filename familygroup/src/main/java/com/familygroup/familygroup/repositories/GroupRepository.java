package com.familygroup.familygroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.familygroup.familygroup.models.Group;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(nativeQuery = true,
    value = "select * from group_members where id = ?1 and created_by = ?2")
    public Group isGroupOwner(Long groupId, Long createdBy);
    
}
