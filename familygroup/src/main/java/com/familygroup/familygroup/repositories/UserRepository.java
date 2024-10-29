package com.familygroup.familygroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.familygroup.familygroup.models.Users;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "select * from users where username = ?1")
    public Users findUserByUsername(String username);

    @Query(nativeQuery = true, value = "select exists(select 1 from users where username = ?1);")
    public boolean isUsername(String username);
    
}
