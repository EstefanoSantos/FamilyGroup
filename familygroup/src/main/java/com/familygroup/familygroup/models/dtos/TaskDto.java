package com.familygroup.familygroup.models.dtos;

import java.time.OffsetDateTime;

import com.familygroup.familygroup.models.Group;
import com.familygroup.familygroup.models.Users;

public record TaskDto(

    Long id,

    String taskName,

    String taskDescription,

    OffsetDateTime creationTime,

    OffsetDateTime finishedAt,

    Long userId,

    Long groupId

) {

} 


