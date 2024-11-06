package com.familygroup.familygroup.models;

import java.sql.Date;
import java.time.OffsetDateTime;

import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
@SequenceGenerator(name = "seq_task", sequenceName = "seq_task", initialValue = 1, allocationSize = 1)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_task")
    private Long id;

    @Column(nullable = false)
    private String taskName;

    @Column(nullable = false)
    private String taskDescription;

    @Column(nullable = false)
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    private OffsetDateTime creationTime;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    private OffsetDateTime finishedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
    name = "created_by_fk"))
    private Users createdBy;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
    name = "group_id_fk"))
    private Group groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public OffsetDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(OffsetDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Group getGroupId() {
        return groupId;
    }

    public void setGroupId(Group groupId) {
        this.groupId = groupId;
    }

    
    
}
