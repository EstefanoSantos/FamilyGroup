package com.familygroup.familygroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.familygroup.familygroup.models.Task;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, 
    value = "select exists (select 1 from user_group ug where ug.group_id = ?1 and ug.user_id = ?2);")
    public boolean isUserPartOfGroup(Long groupId ,Long userId);
    
}
