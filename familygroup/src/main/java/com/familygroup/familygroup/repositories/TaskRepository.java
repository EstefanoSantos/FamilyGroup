package com.familygroup.familygroup.repositories;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    
    @Query(nativeQuery = true,
    value = "select * from task where group_id = ?1;")
    public List<Task> getTasksByGroup(Long groupId);

    @Query(nativeQuery = true,
    value = "select (finished_at) from task where group_id = ?1 and id = ?2;")
    public OffsetDateTime isTaskDone(Long groupId, Long taskId);

    @Modifying
    @Query(nativeQuery = true,
    value = "update task set finished_at = ?1 where group_id = ?2 and id = ?3;")
    public void setTaskDone(OffsetDateTime instance, Long groupdId, Long taskId);
}
