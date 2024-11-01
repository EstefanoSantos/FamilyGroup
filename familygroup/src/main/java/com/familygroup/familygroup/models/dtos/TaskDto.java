package com.familygroup.familygroup.models.dtos;

import java.time.OffsetDateTime;

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


