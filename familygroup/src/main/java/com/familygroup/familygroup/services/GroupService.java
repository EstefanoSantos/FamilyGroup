package com.familygroup.familygroup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.Group;
import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.models.dtos.GroupDto;
import com.familygroup.familygroup.repositories.GroupRepository;
import com.familygroup.familygroup.repositories.UserRepository;
import java.util.Set;
import java.util.HashSet;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public void createGroup(Group group) throws CustomException {

       Users creator = userRepository.findById(group.getCreatedBy().getId())
       .orElseThrow(() -> new CustomException("Creator not found with id " + group.getCreatedBy().getId()));
        
       group.setCreatedBy(creator);

       Set<Users> groupMembers = new HashSet<>();

       for (Users member : group.getUsers()) {

            Users user = userRepository.findById(member.getId())
            .orElseThrow(() -> new CustomException("User not found with id " + member.getId()));

            groupMembers.add(user);

            user.getGroups().add(group);
       }

       group.setUsers(groupMembers);

       groupRepository.save(group);
    }

    public Group mapToNewGroup(GroupDto dto) throws CustomException{

        Group group = new Group();

            group.setGroupName(dto.groupName());
            group.setGroupDescription(dto.groupDescription());
        
            Users creator = userRepository.findById(dto.createBy())
            .orElseThrow(() -> new CustomException("There must contain the group creator's id"));

            group.setCreatedBy(creator);

            Set<Users> groupMembers = new HashSet<>();

            for (Long memberId: dto.users()) {

                Users user = userRepository.findById(memberId)
                .orElseThrow(() -> new CustomException("User not found with id " + memberId));

                groupMembers.add(user);
            }

            return group;

    }
}
