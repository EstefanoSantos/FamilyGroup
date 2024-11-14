package com.familygroup.familygroup.repositories;

import java.util.List;

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

    @Query(nativeQuery = true,
    value =
    "select gm.* from group_members gm join user_group ug on gm.id = ug.group_id where ug.user_id = ?1;")
    public List<Group> getGroupsByUser(Long userId);
    
}
