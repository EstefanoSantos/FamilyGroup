package com.familygroup.familygroup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.Group;
import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.models.dtos.GroupDto;
import com.familygroup.familygroup.repositories.GroupRepository;
import com.familygroup.familygroup.repositories.UserRepository;

import jakarta.transaction.Transactional;

import java.util.Set;
import java.util.HashSet;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public void createGroup(GroupDto dto) throws CustomException {

       Group group = mapToGroup(dto);

       groupRepository.save(group);
    }

    public GroupDto getGroupById(Long id) throws CustomException {

        Group group = groupRepository.findById(id)
        .orElseThrow(() -> new CustomException("Group cannot be found."));

        return mapToGroupDto(group);
    }

    @Transactional
    public void deleteGroupById(Long groupId, Long createdBy) throws CustomException {

        Group group = groupRepository.isGroupOwner(groupId, createdBy);

        if (group == null) {
            throw new CustomException("Group was not found.");
        }

        group.getUsers().forEach(user -> user.getGroups().remove(group));

        groupRepository.delete(group);
    }

    private Group mapToGroup(GroupDto dto) throws CustomException{

        Group group = new Group();

            group.setGroupName(dto.groupName());
            group.setGroupDescription(dto.groupDescription());
        
            Users creator = userRepository.findById(dto.createdBy())
            .orElseThrow(() -> new CustomException("User not found with id " + dto.createdBy()));

            group.setCreatedBy(creator);

            group.getUsers().add(creator);

            creator.getGroups().add(group);

            Set<Users> groupMembers = new HashSet<>();

            for (Long memberId: dto.users()) {

                Users user = userRepository.findById(memberId)
                .orElseThrow(() -> new CustomException("User not found with id " + memberId));

                groupMembers.add(user);

                user.getGroups().add(group);
            }

            group.setUsers(groupMembers);

            return group;

    }

    private GroupDto mapToGroupDto(Group group) {

        Set<Long> groupMembers = new HashSet<>();

        for (Users member : group.getUsers()) {
            groupMembers.add(member.getId());
        }

        GroupDto dto = new GroupDto(group.getId(),
        group.getGroupName(),
        group.getGroupDescription(), 
        group.getCreatedBy().getId(), 
        groupMembers);

        return dto;
    }
}
