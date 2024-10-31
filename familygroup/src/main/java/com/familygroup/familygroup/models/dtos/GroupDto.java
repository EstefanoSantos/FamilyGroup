package com.familygroup.familygroup.models.dtos;
import java.util.Set;

public record GroupDto(

    Long id,

    String groupName,

    String groupDescription,

    Long createdBy,

    Set<Long> users
    
) {
    
}
