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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    // creates a new group
    public void createGroup(GroupDto dto) throws CustomException {

        Group group = mapToGroup(dto);

        groupRepository.save(group);
    }

    // Find a group by id
    public GroupDto getGroupById(Long id) throws CustomException {

        // check if is a valid group
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Group cannot be found."));

        return mapToGroupDto(group);
    }

    // Get user's group
    public List<GroupDto> getGroupsByUser(Long userId) throws CustomException {

        // check if user exists
        boolean isUser = userRepository.existsById(userId);

        if (isUser == false) {
            throw new CustomException("There's no user with the given id.");
        }

        // Try to retrive data from groups table
        List<Group> groups = groupRepository.getGroupsByUser(userId);

        if (groups.isEmpty()) {
            throw new CustomException("User's groups not found");
        }

        // Maps a list of groups to groupsDto
        return mapToGroupsDto(groups);

    }

    // delete group by id
    @Transactional
    public void deleteGroupById(Long groupId, Long createdBy) throws CustomException {

        // check if group exists and user own it
        Group group = groupRepository.isGroupOwner(groupId, createdBy);

        if (group == null) {
            throw new CustomException("Group was not found.");
        }

        // remove users from group before deleting it
        group.getUsers().forEach(user -> user.getGroups().remove(group));

        groupRepository.delete(group);
    }

    // Maps Dto to Group class
    private Group mapToGroup(GroupDto dto) throws CustomException {

        Group group = new Group();

        group.setGroupName(dto.groupName());
        group.setGroupDescription(dto.groupDescription());

        // check if group creator user exists
        Users creator = userRepository.findById(dto.createdBy())
                .orElseThrow(() -> new CustomException("User not found with id " + dto.createdBy()));

        // set user found in the field created_by as the group creator
        group.setCreatedBy(creator);

        // add group creator to Set of users in the group
        group.getUsers().add(creator);

        // add group to to Set of groups in user
        creator.getGroups().add(group);

        /*
         * gets each individual user that might be send in http post request
         * and add to the group
         */
        Set<Users> groupMembers = new HashSet<>();

        if (dto.users() != null) {
            for (Long memberId : dto.users()) {

                Users user = userRepository.findById(memberId)
                        .orElseThrow(() -> new CustomException("User not found with id " + memberId));

                groupMembers.add(user);

                user.getGroups().add(group);
            }
        }

        group.setUsers(groupMembers);

        return group;

    }

    // map Group to GroupDto
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

    // Receives a list of groups and maps to DTO objects
    private List<GroupDto> mapToGroupsDto(List<Group> groups) {

        List<GroupDto> dtos = new ArrayList<>();

        // Iterates through each group and map it to GroupDto
        for (Group group : groups) {

            // Each group have a set of users that makes part of it
            // Here we iterate through each user id and add it to the set
            Set<Long> groupMembers = new HashSet<>();

            for (Users member : group.getUsers()) {
                groupMembers.add(member.getId());
            }

            // Now we take group field and maps to Dto
            // The set previously created is added to the dto set users attribute
            GroupDto dto = new GroupDto(group.getId(),
                    group.getGroupName(),
                    group.getGroupDescription(),
                    group.getCreatedBy().getId(),
                    groupMembers);

            // add the DTO to the list of dtos
            dtos.add(dto);
        }

        // Finally we return the List
        return dtos;
    }
}
