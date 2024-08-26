package com.spring.chatApp.data.repository;


import com.spring.chatApp.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    User findMessageByUsername(String username);




}
